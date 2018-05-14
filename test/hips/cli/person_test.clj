(ns hips.cli.person-test
  (:require [clojure.test :refer :all]
            [hips.cli.person :refer :all]))

(def chip-foose-map
  {:first-name "Chip" :last-name "Foose" :gender "M" :favorite-color "MetallicOrange" :date-of-birth (parse-date "2004-10-12")})

(def chris-jacobs-map
  {:first-name "Chris" :last-name "Jacobs" :gender "M" :favorite-color "Black" :date-of-birth (parse-date "2008-06-26")})

(def courtney-hansen-map
  {:first-name "Courtney" :last-name "Hansen" :gender "F" :favorite-color "Blue" :date-of-birth (parse-date "2005-03-17")})

(def chip-foose-comma
  "Chip,Foose,M,MetallicOrange,2004-10-12")

(def chip-foose-comma-output
  "Chip,Foose,M,MetallicOrange,10/12/2004")

(def chip-foose-pipe
  "Chip|Foose|M|MetallicOrange|2004-10-12")

(def chip-foose-space
  "Chip Foose M MetallicOrange 2004-10-12")

(def chris-jacobs-comma
  "Chris,Jacobs,M,Black,2008-06-26")

(def chris-jacobs-pipe
  "Chris|Jacobs|M|Black|2008-06-26")

(def chris-jacobs-space
  "Chris Jacobs M Black 2008-06-26")

(def courtney-hansen-comma
  "Courtney,Hansen,F,Blue,2005-03-17")

(def courtney-hansen-pipe
  "Courtney|Hansen|F|Blue|2005-03-17")

(def courtney-hansen-space
  "Courtney Hansen F Blue 2005-03-17")

(deftest sort-by-gender-test
  (testing "sort collection of people by gender and then last name in ascending order"
    ; setup
    (let [ppl (atom [])]
      (swap! ppl conj chris-jacobs-map)
      (swap! ppl conj courtney-hansen-map)
      (swap! ppl conj chip-foose-map)
      ; execute and verify
      (is (= [courtney-hansen-map chip-foose-map chris-jacobs-map] (sort-by-gender @ppl))))))

(deftest sort-by-date-of-birth-test
  (testing "sort collection of people by date of birth in ascending order"
    ; setup
    (let [ppl (atom [])]
      (swap! ppl conj chris-jacobs-map)
      (swap! ppl conj chip-foose-map)
      (swap! ppl conj courtney-hansen-map)
      ; execute and verify
      (is (= [chip-foose-map courtney-hansen-map chris-jacobs-map] (sort-by-date-of-birth @ppl))))))

(deftest sort-by-last-name-test
  (testing "sort collection of people by last name descending order"
    ; setup
    (let [ppl (atom [])]
      (swap! ppl conj chip-foose-map)
      (swap! ppl conj chris-jacobs-map)
      (swap! ppl conj courtney-hansen-map)
      ; execute and verify
      (is (= [chris-jacobs-map courtney-hansen-map chip-foose-map] (sort-by-last-name @ppl))))))

(deftest to-csv-test
  (testing "write person map as csv string"
    (is (= chip-foose-comma-output (to-csv chip-foose-map)))))
