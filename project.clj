(defproject org.clojars.j1mr10rd4n/clj-soap "0.2.1-SNAPSHOT"
  :description "SOAP Client and Server using Apache Axis2."
  :url "https://github.com/j1mr10rd4n/clj-soap"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.apache.axis2/axis2-adb "1.6.4"]
                 [org.apache.axis2/axis2-transport-http "1.6.4"]
                 [org.apache.axis2/axis2-transport-local "1.6.4"]]
  :source-paths ["src" "test"]
  :aot [clj-soap.test.core])
