(ns hips-cli.person
  (:require
    [clojure.string :as string]
    ))

(def ^{:added "0.3.0"} people (atom {}))

(defn ^{:added "0.3.0"} add-mapped [mapped]
  (swap! people conj mapped)
  )

(defn ^{:added "0.3.0"} add-delimited [delimited]
  (def pv (string/split delimited #"[,| ]"))
  (if (= (count pv) 5)
    (add-mapped (zipmap [:first-name :last-name :gender :favorite-color :date-of-birth] pv))
    (println "Invalid delimiter or record layout, ignoring:" (str "'" delimited "'"))
    ))
