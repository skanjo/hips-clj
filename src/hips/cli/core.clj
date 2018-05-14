(ns hips.cli.core
  (:require
    [hips.cli.person :as person]
    [clojure.java.io :as io]
    [clojure.string :as string]
    [clojure.tools.cli :refer [parse-opts]]
    [trptcolin.versioneer.core :as version])
  (:gen-class))

(def
  cli-spec [["-v" "--version" "Version of this application"]
            ["-h" "--help" "Prints this help message"]])

(defn cli-help-msg
  [summary]
  (->> ["HipsCli merges and sorts one or more files containing person records for profit!"
        ""
        "Usage: HipsCli [options] [file ...]"
        ""
        "Options:"
        summary
        ""]
       (string/join \newline)))

(defn cli-version-msg
  []
  (str "HipsCli" " " (version/get-version "io.xorshift" "hips-cli")))


(defn- cli-error-msg
  [errors]
  (->> ["The following errors occurred while parsing your command:"
        ""
        (string/join \newline errors)]
       (string/join \newline)))

(defn cli-parse-command
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
      {:exit-message (cli-help-msg summary)})))

(defn exit
  [status msg]
  (println msg)
  (System/exit status))

(defn read-files
  [files ppl]
  (doseq [f files]
    (if (.canRead (io/file f))
      (with-open [rdr (io/reader f)]
        (doseq [line (line-seq rdr)]
          ;(person/add line person/people)
          ;(swap! person/people conj (person/from-csv line))
          (swap! ppl conj (person/from-csv line))))
      ;(println (person/from-csv line))))

      (prn "Cannot read file, ignoring:" (str "'" f "'")))))

(defn write-sorted-lists
  [ppl]
  (println "OUTPUT 1 - SORTED BY GENDER AND THEN BY LAST NAME ASCENDING")
  (doseq [p (person/sort-by-gender @ppl)]
    (print (str (person/to-csv p) \newline)))
  (println)

  (println "OUTPUT 2 - SORTED BY BIRTH DATE ASCENDING")
  (doseq [p (person/sort-by-date-of-birth @ppl)]
    (print (str (person/to-csv p) \newline)))
  (println)

  (println "OUTPUT 3 - SORTED BY LAST NAME DESCENDING")
  (doseq [p (person/sort-by-last-name @ppl)]
    (print (str (person/to-csv p) \newline))))

(defn merge-and-sort
  [files]
  (let [ppl (atom [])]
    (read-files files ppl)
    (write-sorted-lists ppl)))

(defn -main
  [& args]
  (let [{:keys [arguments exit-message ok?]} (cli-parse-command args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (merge-and-sort arguments))))
