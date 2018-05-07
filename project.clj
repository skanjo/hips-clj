(defproject hips-clj "0.1.0-SNAPSHOT"
  :description "Merge and sort records"
  :url "https://github.com/skanjo/hips-clj"
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot hips-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
