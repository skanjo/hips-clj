(ns hips.http.core
  (:require [hips.cli.person :as person]
            [trptcolin.versioneer.core :as version]))

(def people (atom []))

(defn app-version
  []
  (str "HipsHTTP " (version/get-version "io.xorshift" "hips-clj-http") \newline))

(defn health-check
  []
  (str "OK" \newline))

(defn add-person
  [body]
  (let [p (person/from-csv body)]
    (println p)
    (swap! people conj p))
  ;(str (println (person/from-csv body) \newline))
  ()
  )

(defn sort-by-gender
  []
  ;(person/sort-by-gender @people)
  (str "sort by gender" \newline)
  )

(defn sort-by-date-of-birth
  []
  (str "sort-by-date-of-birth" \newline))

(defn sort-by-last-name
  []
  (str "sort by last name" \newline))

