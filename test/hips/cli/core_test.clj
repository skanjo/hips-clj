(ns hips.cli.core-test
  (:require [clojure.test :refer :all]
            [hips.cli.core :refer :all]
            [trptcolin.versioneer.core :as version]))

(deftest cli-version-msg-test
  (testing "version message format"
    (with-redefs [version/get-version (fn [group artifact] "major.minor.patch")]
      (is (= "HipsCli major.minor.patch" (cli-version-msg))))))
