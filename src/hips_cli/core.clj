(ns hips-cli.core
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
  (str "HipsCli" " " (version/get-version "io.xorshift" "hips-cli"))
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
      {:exit-message (cli-help-msg summary) :ok? true}

      (:version options)
      {:exit-message (cli-version-msg) :ok? true}

      errors
      {:exit-message (cli-error-msg errors)}

      (> (count arguments) 0)
      {:arguments arguments}

      :else
      {:exit-message (cli-help-msg summary)}
      )))

(defn- exit [status msg]
  (println msg)
  (System/exit status)
  )

(defn merge-and-sort [arguments]
  (println "merge-and-sort" (clojure.string/join " " arguments))
  (System/exit 0)
  )

(defn ^{:added "0.1.0"} -main [& args]
  (let [{:keys [arguments exit-message ok?]} (cli-parse-command args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (merge-and-sort arguments))))
