(defproject io.xorshift/hips-clj-parent "0.6.0-SNAPSHOT"

  :description "Merge and sort records"

  :url "https://github.com/skanjo/hips-clj"

  :min-lein-version "2.0.0"

  :plugins [[lein-modules "0.3.11"]
            [lein-pprint "1.2.0"]]

  :profiles {:uberjar {:aot :all}}

  :modules {:subprocess nil}
  )
