(defproject io.xorshift/hips-clj-cli "0.6.0-SNAPSHOT"

  :description "Merge and sort records"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.cli "0.3.7"]
                 [trptcolin/versioneer "0.2.0"]]

  :plugins [[lein-pprint "1.2.0"]]

  :profiles {:uberjar {:aot :all}}

  :main ^:skip-aot hips.cli.core

  :target-path "target/%s"

  )
