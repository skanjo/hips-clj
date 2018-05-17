(defproject io.xorshift/hips-clj-http "0.6.0-SNAPSHOT"

  :description "HTTP server accepts person records and produces sorted lists"

  :dependencies [[io.xorshift/hips-clj-people "0.6.0-SNAPSHOT"]
                 ; user of :version not working with ring for some reason
                 ;[io.xorshift/hips-clj-people :version]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.cli "0.3.7"]
                 [trptcolin/versioneer "0.2.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.1"]]

  :plugins [[lein-modules "0.3.11"]
            [lein-ring "0.12.4"]]

  :ring {:handler hips.http.core/app}

  :profiles {
             ;:uberjar {:aot :all}
             :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.3.2"]]}}

  )
