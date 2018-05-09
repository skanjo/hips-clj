(defproject hips-clj "0.1.0-SNAPSHOT"
  :description "Merge and sort records"
  :url "https://github.com/skanjo/hips-clj"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.cli "0.3.7"]
                 [trptcolin/versioneer "0.2.0"]]
  :main ^:skip-aot hips-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [])
