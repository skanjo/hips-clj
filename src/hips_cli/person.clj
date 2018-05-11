(ns hips-cli.person
  (:require
    [clojure.string :as string]))

(def
  ^{:added "0.3.0"}
  people (atom []))

(def
  ^{:added "0.3.0"}
  in-date-format (java.text.SimpleDateFormat. "yyyy-MM-dd"))

(def
  ^{:added "0.4.0"}
  out-date-format (java.text.SimpleDateFormat. "M/dd/yyyy"))

(defn- parse-date
  {:added "0.3.0"}
  [dt]
  (.parse in-date-format dt))

(defn- format-date
  {:added "0.4.0"}
  [dt]
  (.format out-date-format dt))

(defn- intern-record
  {:added "0.3.0"}
  [rec]
  (update rec :date-of-birth parse-date))

(defn- extern-record
  {:added "0.4.0"}
  [rec]
  (update rec :date-of-birth format-date))

(defn add
  {:added "0.3.0"}
  [person]
  (let [pv (string/split person #"[,| ]")]
    (if (= (count pv) 5)
      (let [pm (intern-record (zipmap [:first-name :last-name :gender :favorite-color :date-of-birth] pv))]
        (swap! people conj pm))
      (println "Invalid delimiter or record layout, ignoring:" (str "'" person "'")))))

(defn sort-by-gender
  {:added "0.3.0"}
  []
  (sort-by (juxt :gender :last-name) @people))

(defn sort-by-date-of-birth
  {:added "0.3.0"}
  []
  (sort-by :date-of-birth @people))

(defn sort-by-last-name
  {:added "0.3.0"}
  []
  (sort-by :last-name #(compare %2 %1) @people))

(defn to-csv
  {:added "0.4.0"}
  [pm]
  (str (string/join "," (vals (extern-record pm))) \newline))
