(defproject io.xorshift/hips-clj-http "0.6.0-SNAPSHOT"

  :description "HTTP server accepts person records and produces sorted lists"

  :dependencies [[io.xorshift/hips-clj-people "0.6.0-SNAPSHOT"]
                 ; user of :version not working with ring for some reason
                 ;[io.xorshift/hips-clj-people :version]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.cli "0.3.7"]
                 [trptcolin/versioneer "0.2.0"]]

  :plugins [[lein-modules "0.3.11"]]

  )
