(ns hips.http.core
  (:require [ring.adapter.jetty :as jetty])
  (:gen-class))

(defn handler [request]
  {:status  200
   :headers {"content-type" "text/html"}
   :body    "Hips HTTP Sample Project"})

(defn -main
  [& args]
  (jetty/run-jetty handler {:port 3000}))
