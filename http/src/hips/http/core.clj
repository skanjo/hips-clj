(ns hips.http.core
  (:require [hips.cli.person :as person]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [cheshire.core :as json])
  (:gen-class))

(def chip-foose-map
  {:first-name     "Chip"
   :last-name      "Foose"
   :gender         "M"
   :favorite-color "MetallicOrange"
   :date-of-birth  (person/parse-date "2004-10-12")})

(def chris-jacobs-map
  {:first-name     "Chris"
   :last-name      "Jacobs"
   :gender         "M"
   :favorite-color "Black"
   :date-of-birth  (person/parse-date "2008-06-26")})

(def courtney-hansen-map
  {:first-name     "Courtney"
   :last-name      "Hansen"
   :gender         "F"
   :favorite-color "Blue"
   :date-of-birth  (person/parse-date "2005-03-17")})

(def people
  [chip-foose-map chris-jacobs-map courtney-hansen-map])

(defn health
  [request]
  {:status  200
   :headers {"content-type" "text/plain"}
   :body    (str "OK" \newline)})

(defn render-json
  [c]
  {:status  200
   :headers {"content-type" "application/json"}
   :body    (json/generate-string c {:date-format "M/dd/yyyy" :pretty true})})

(defn not-found
  []
  {:status  404
   :headers {"content-type" "text/plain"}
   :body    "Not Found"})

(defn people-sorted-by-gender
  [ppl]
  (render-json (person/sort-by-gender ppl)))

(defn people-sorted-by-date-of-birth
  [ppl]
  (render-json (person/sort-by-date-of-birth ppl)))

(defn people-sorted-by-last-name
  [ppl]
  (render-json (person/sort-by-last-name ppl)))

(defn handler
  [{uri :uri method :request-method}]
  (cond
    (and (= method :get) (= uri "/records/gender")) (people-sorted-by-gender people)
    (and (= method :get) (= uri "/records/birthday")) (people-sorted-by-date-of-birth people)
    (and (= method :get) (= uri "/records/name")) (people-sorted-by-last-name people)
    :else (not-found)))

(def reloadable-handler
  (wrap-reload #'handler))

(defn -main
  [& args]
  (jetty/run-jetty #'reloadable-handler {:port 3000}))
