(ns hips-cli.person
  (:require
    [clojure.string :as string]))

(def ^{:added "0.3.0"} people (atom []))

(def ^{:added "0.3.0"} in-date-format (java.text.SimpleDateFormat. "yyyy-MM-dd"))

(defn- ^{:added "0.3.0"} parse-date [dt]
  (.parse in-date-format dt))

(defn- ^{:added "0.3.0"} normalize-record [rec]
  (update rec :date-of-birth parse-date))

(defn ^{:added "0.3.0"} add [person]
  (let [pv (string/split person #"[,| ]")]
    (if (= (count pv) 5)
      (let [pm (normalize-record (zipmap [:first-name :last-name :gender :favorite-color :date-of-birth] pv))]
        (swap! people conj pm))
      (println "Invalid delimiter or record layout, ignoring:" (str "'" person "'")))))

(defn ^{:added "0.3.0"} sort-by-gender []
  (sort-by (juxt :gender :last-name) @people))

(defn ^{:added "0.3.0"} sort-by-date-of-birth []
  (sort-by :date-of-birth @people))

(defn ^{:added "0.3.0"} sort-by-last-name []
  (sort-by :last-name #(compare %2 %1) @people))

(defn ^{:added "0.4.0"} to-csv [pm]
  (str (string/join "," (vals pm)) \newline))
