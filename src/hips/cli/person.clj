(ns hips.cli.person
  (:require
    [clojure.string :as string]))

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

(def field-mapping
  (array-map
    :first-name identity
    :last-name identity
    :gender identity
    :favorite-color identity
    :date-of-birth #(parse-date %)))

(defn translate-field-values [m]
  (into {}
        (for [[field parser] field-mapping]
          [field (parser (get m field))])))

(defn csv-to-map
  [csv]
  (zipmap [:first-name :last-name :gender :favorite-color :date-of-birth] (string/split csv #"[,| ]")))

(defn from-csv
  [csv]
  (translate-field-values (csv-to-map csv)))

(defn sort-by-gender
  [v]
  (sort-by (juxt :gender :last-name) v))

(defn sort-by-date-of-birth
  [v]
  (sort-by :date-of-birth v))

(defn sort-by-last-name
  [v]
  (sort-by :last-name #(compare %2 %1) v))

(defn to-csv
  [pm]
  (string/join "," (vals (update pm :date-of-birth format-date))))

