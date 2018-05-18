(defproject io.xorshift/hips-clj-http "0.7.0-SNAPSHOT"

  :description "HTTP server accepts person records and produces sorted lists"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]]

  :plugins [[lein-modules "0.3.11"]]

  :profiles {:uberjar {:aot :all}}

  :main hips.http.core

  )
