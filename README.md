# clj-soap

clj-soap is SOAP server and client using Apache Axis2.


## Usage

### Client

You can call remote SOAP method as following:
```clojure
(require '[clj-soap.core :as soap])

(let [client (soap/client-fn "http://www.webservicex.com/globalweather.asmx?WSDL")]
   (client :GetWeather "Murcia" "Spain" ))

```
### Server

To make SOAP service:
```clojure
(require '[clj-soap.core :as soap])

;; Defining service class
(soap/defservice my.some.SoapClass
  (someMethod ^String [^Integer x ^String s]
              (str "x is " x "\ts is " s)))

;; Start SOAP Service
(serve "my.some.SoapClass")
```
`defservice` needs to be AOT-compiled.
For example, `lein compile` before running server.

#### Type Hint

SOAP services need typehints.
`String` for arguments and `void` for return value,
if you don't specify typehints.

## Other SOAP-related Clojure projects that might interest you

* [soap-box](https://github.com/slipset/soap-box) -- an example of how to build a simple SOAP web service (server).


## License

Copyright (C) 2011 Tetsuya Takatsuru

Distributed under the Eclipse Public License, the same as Clojure.

