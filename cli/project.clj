(defproject io.xorshift/hips-clj-cli "0.7.0-SNAPSHOT"

  :description "CLI application that reads one or more files and prints three sorted lists"

  :dependencies [[io.xorshift/hips-clj-people :version]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.cli "0.3.7"]
                 [trptcolin/versioneer "0.2.0"]]

  :plugins [[lein-modules "0.3.11"]]

  :profiles {:uberjar {:aot :all}}

  :main hips.cli.core)
