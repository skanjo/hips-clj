(ns hips.http.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]])
  (:gen-class))

(defn handler [request]
  {:status  200
   :headers {"content-type" "text/html"}
   :body    (str "Hips HTTP Sample Project - foo" \newline)})

(def reloadable-handler
  (wrap-reload #'handler))

(defn -main
  [& args]
  (jetty/run-jetty reloadable-handler {:port 3000}))
