(ns hips-cli.person-test
  (:require [clojure.test :refer :all]
            [hips-cli.person :refer :all]))

(def chin-coard-map
  {:first-name "Chin" :last-name "Coard" :gender "F" :favorite-color "LightBlue" :date-of-birth (parse-date "1982-05-12")})

(def chin-coard-csv
  "Chin,Coard,F,LightBlue,5/12/1982\n")

(deftest to-csv-test
  (testing "write person map as csv string"
    (is (= chin-coard-csv (to-csv chin-coard-map)))))
