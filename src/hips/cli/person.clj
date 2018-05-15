(ns hips.cli.person
  (:require
    [clojure.string :as string])
  (:import (java.text SimpleDateFormat)))

(def in-date-format
  "Reusable date parser instance with format yyyy-MM-dd"
  (SimpleDateFormat. "yyyy-MM-dd"))

(def out-date-format
  "Reusable date format instance with format M/dd/yyyy"
  (SimpleDateFormat. "M/dd/yyyy"))

(defn parse-date
  "Parse a date string using the format defined by in-date-format."
  [dt]
  (.parse in-date-format dt))

(defn format-date
  "Format date instance to string using format defined in out-date-format."
  [dt]
  (.format out-date-format dt))

(def field-mapping
  "Define rules for converting raw field string values to appropriate type
  using specified parser function. Returns an array-map of fields in order:
  first-name, last-name, gender, favorite-color, and date-of-birth having
  values that define parser function."
  (array-map
    :first-name identity
    :last-name identity
    :gender identity
    :favorite-color identity
    :date-of-birth parse-date))

(defn translate-field-values
  "Parse field value strings of a person map to the appropriate type using
  field-mapping returning a map containing the parsed values."
  [m]
  (into {}
        (for [[field parser] field-mapping]
          [field (parser (get m field))])))

(defn from-csv
  "Read delimited person record and return a map containing the parsed field
  values. Only supports the delimiters comma, pipe, and space. The field values
  should not be quoted nor should the values contain the delimiter used. The
  following field values are expected in order: first-name, last-name, gender,
  favorite-color, and date-of-birth. All fields are required and must not have
  empty values. The date-of-birth is expected to be formatted as yyyy-MM-dd."
  [csv]
  (translate-field-values
    (zipmap [:first-name :last-name :gender :favorite-color :date-of-birth]
            (string/split csv #"[,| ]"))))

(defn to-csv
  "Write a person map to a comma delimited string. The field values are not
  quoted. The date-of-birth is formatted as M/dd/yyyy. The field values are are
  written in the following order: first-name, last-name, gender,
  favorite-color, and date-of-birth"
  [pm]
  (->> (update pm :date-of-birth format-date)
       (vals)
       (string/join ",")))

(defn sort-by-gender
  "Sort a vector of person maps by gender and last name in ascending order
   returning a sorted sequence."
  [v]
  (sort-by (juxt :gender :last-name) v))

(defn sort-by-date-of-birth
  "Sort a vector of person maps by date of birth in ascending order returning
   a sorted sequence."
  [v]
  (sort-by :date-of-birth v))

(defn sort-by-last-name
  "Sort a vector of person maps by last name in descending order returning a
   sorted sequence."
  [v]
  (sort-by :last-name #(compare %2 %1) v))
