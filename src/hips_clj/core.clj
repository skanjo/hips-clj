(ns hips-clj.core
  (:require
    [clojure.string :as string]
    [clojure.tools.cli :refer [parse-opts]]
    [trptcolin.versioneer.core :as version])
  (:gen-class))

(def ^{:added "0.1.0"} cli-spec
  [["-v" "--version" "Version of this application"]
   ["-h" "--help" "Prints this help message"]])

(defn- ^{:added "0.1.0"} cli-help-msg [summary]
  (->> ["HipsCli merges and sorts one or more files containing person records for profit!"
        ""
        "Usage: HipsCli [options] [file ...]"
        ""
        "Options:"
        summary
        ""]
       (string/join \newline)))

(defn- ^{:added "0.1.0"} cli-version-msg []
  (str "HipsCli" " " (version/get-version "hips-clj" "hips-clj"))
  )

(defn- ^{:added "0.1.0"} cli-error-msg [errors]
  (->> ["The following errors occurred while parsing your command:"
        ""
        (string/join \newline errors)]
       (string/join \newline)))

(defn- ^{:added "0.1.0"} cli-parse-command [args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-spec)]
    (cond
      (:help options)
      {:command :help :message (cli-help-msg summary)}

      (:version options)
      {:command :version :message (cli-version-msg)}

      errors
      {:command :errors :message (cli-error-msg errors)}

      :else
      {:command :help :message (cli-help-msg summary)}
      )))

(defn ^{:added "0.1.0"} -main [& args]
  (let [{:keys [command message]} (cli-parse-command args)]
    (case command
      :help (println message)
      :version (println message)
      :exit message (println message)
      )
    ))

