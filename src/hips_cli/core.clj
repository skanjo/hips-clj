(ns hips-cli.core
  (:require
    [hips-cli.person :as person]
    [clojure.java.io :as io]
    [clojure.string :as string]
    [clojure.tools.cli :refer [parse-opts]]
    [trptcolin.versioneer.core :as version])
  (:gen-class))

(def
  ^{:added "0.2.0"}
  cli-spec [["-v" "--version" "Version of this application"]
            ["-h" "--help" "Prints this help message"]])

(defn- cli-help-msg
  {:added "0.2.0"}
  [summary]
  (->> ["HipsCli merges and sorts one or more files containing person records for profit!"
        ""
        "Usage: HipsCli [options] [file ...]"
        ""
        "Options:"
        summary
        ""]
       (string/join \newline)))

(defn- cli-version-msg
  {:added "0.2.0"}
  []
  (str "HipsCli" " " (version/get-version "io.xorshift" "hips-cli"))
  )

(defn- cli-error-msg
  {:added "0.2.0"}
  [errors]
  (->> ["The following errors occurred while parsing your command:"
        ""
        (string/join \newline errors)]
       (string/join \newline)))

(defn- cli-parse-command
  {:added "0.2.0"}
  [args]
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

(defn- exit
  {:added "0.2.0"}
  [status msg]
  (println msg)
  (System/exit status)
  )

(defn- merge-and-sort
  {:added "0.2.0"}
  [files]
  (doseq [f files]
    (if (.canRead (io/file f))
      (with-open [rdr (io/reader f)]
        (doseq [line (line-seq rdr)]
          (person/add line)))
      (prn "Cannot read file, ignoring:" (str "'" f "'"))))

  (println "OUTPUT 1 - SORTED BY GENDER AND THEN BY LAST NAME ASCENDING")
  (doseq [p (person/sort-by-gender)]
    (print (person/to-csv p)))
  (println)
  (println "OUTPUT 2 - SORTED BY BIRTH DATE ASCENDING")
  (doseq [p (person/sort-by-date-of-birth)]
    (print (person/to-csv p)))
  (println)
  (println "OUTPUT 3 - SORTED BY LAST NAME DESCENDING")
  (doseq [p (person/sort-by-last-name)]
    (print (person/to-csv p))))

(defn -main
  {:added "0.1.0"}
  [& args]
  (let [{:keys [arguments exit-message ok?]} (cli-parse-command args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (merge-and-sort arguments))))
