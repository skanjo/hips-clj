(ns hips.http.core
  (:require [hips.cli.person :as person]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.util.request :as req]
            [cheshire.core :as json]
            [trptcolin.versioneer.core :as version])

  (:gen-class))

(def people (atom []))

(defn health
  []
  {:status  200
   :headers {"content-type" "text/plain"}
   :body    (str "OK" \newline)})

(defn version
  []
  {:status  200
   :headers {"content-type" "text/plain"}
   :body    (str (str "HipsHttp " (version/get-version "io.xorshift" "hips-clj-http")) \newline)})

(defn render-json
  [c]
  {:status  200
   :headers {"content-type" "application/json"}
   :body    (str (json/generate-string c {:date-format "M/dd/yyyy" :pretty true}) \newline)})

(defn not-found
  []
  {:status  404
   :headers {"content-type" "text/plain"}
   :body    (str "Not Found" \newline)})

(defn add-person
  [csv ppl]
  (swap! ppl conj (person/from-csv csv))
  {:status  200
   :headers {"content-type" "text/plain"}
   :body    (str "OK" \newline (person/from-csv csv) \newline)})

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
  [request]
  (let [{:keys [request-method uri]} request]
    (cond
      (and (= request-method :post) (= uri "/records")) (add-person (req/body-string request) people)
      (and (= request-method :get) (= uri "/records/gender")) (people-sorted-by-gender @people)
      (and (= request-method :get) (= uri "/records/birthday")) (people-sorted-by-date-of-birth @people)
      (and (= request-method :get) (= uri "/records/name")) (people-sorted-by-last-name @people)
      (and (= request-method :get) (= uri "/health")) (health)
      (and (= request-method :get) (= uri "/version")) (version)
      :else (not-found))))

(def reloadable-handler
  (wrap-reload #'handler))

(defn -main
  [& args]
  (jetty/run-jetty #'reloadable-handler {:port 8080}))
