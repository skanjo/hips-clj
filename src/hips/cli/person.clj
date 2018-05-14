(ns hips.cli.person
  (:require
    [clojure.string :as string]))

(def
  people (atom []))

(def
  in-date-format (java.text.SimpleDateFormat. "yyyy-MM-dd"))

(def
  out-date-format (java.text.SimpleDateFormat. "M/dd/yyyy"))

(defn parse-date
  [dt]
  (.parse in-date-format dt))

(defn- format-date
  [dt]
  (.format out-date-format dt))

(defn- intern-record
  [rec]
  (update rec :date-of-birth parse-date))

(defn- extern-record
  [rec]
  (update rec :date-of-birth format-date))

(defn add
  [person peeps]
  (let [pv (string/split person #"[,| ]")]
    (if (= (count pv) 5)
      (let [pm (intern-record (zipmap [:first-name :last-name :gender :favorite-color :date-of-birth] pv))]
        (swap! peeps conj pm))
      (println "Invalid delimiter or record layout, ignoring:" (str "'" person "'")))))

(defn sort-by-gender
  [peeps]
  (sort-by (juxt :gender :last-name) @peeps))

(defn sort-by-date-of-birth
  [peeps]
  (sort-by :date-of-birth @peeps))

(defn sort-by-last-name
  [peeps]
  (sort-by :last-name #(compare %2 %1) @peeps))

(defn to-csv
  [pm]
  (string/join "," (vals (extern-record pm))))
