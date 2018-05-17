(ns hips.http.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [trptcolin.versioneer.core :as version]))

(defroutes app-routes
           (GET "/version" [] (str "HipsHTTP " (version/get-version "io.xorshift" "hips-clj-http")))
           (GET "/health" [] "OK")
           ;(POST "/records" [] "??")
           (GET "/records/gender" [] "sort by gender")
           (GET "/records/birthdate" [] "sort by date of birth")
           (GET "/records/name" [] "sort by last name")
           (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
