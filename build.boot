(def boot-version
  (get (boot.App/config) "BOOT_VERSION" "2.7.1"))

(deftask from-lein
  "Use project.clj as source of truth as far as possible"
  []
  (let [lein-proj (let [l (-> "project.clj" slurp read-string)]
                    (merge (->> l (drop 3) (partition 2) (map vec) (into {}))
                           {:project (second l) :version (nth l 2)}))]
    (merge-env! :repositories (:repositories lein-proj))
    (set-env!
      :certificates   (:certificates lein-proj)
      :source-paths   (or (into #{} (:source-paths lein-proj)) #{"src"})
      :resource-paths (or (into #{} (:resource-paths lein-proj)) #{"resources"})
      :dependencies   (into (:dependencies lein-proj)
                            `[[boot/core ~boot-version   :scope "provided"]
                              [adzerk/bootlaces "0.1.13" :scope "test"]
                              [adzerk/boot-test "1.2.0" :scope "test"]]))

    (require '[adzerk.bootlaces :refer :all])
    ((resolve 'bootlaces!) (:version lein-proj))
    (task-options!
      repl (:repl-options lein-proj {})
      aot  (let [aot (:aot lein-proj)
                 all? (or (nil? aot) (= :all aot))
                 ns (when-not all? (set aot))]
             {:namespace ns :all all?})
      jar  {:main (:main lein-proj)}
      pom  {:project     (symbol (:project lein-proj))
            :version     (:version lein-proj)
            :description (:description lein-proj)
            :url         (:url lein-proj)
            :scm         (:scm lein-proj)
            :license     (get lein-proj :license)}))
  identity)

(deftask boot-test-with-require
  []
  (require '[adzerk.boot-test :refer :all])
  (let [boot-test-task (resolve 'adzerk.boot-test/test)]
    (fn [next-handler]
      (fn [fileset]
        (let [boot-test-middleware (boot-test-task)
              boot-test-handler (boot-test-middleware next-handler)
              fs' (boot-test-handler fileset)]
          fs')))))

(deftask from-lein-test
  []
  (comp
    (from-lein)
    (aot)
    (boot-test-with-require)))
