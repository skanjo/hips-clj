(ns hips.cli.core
  (:require
    [hips.cli.person :as person]
    [clojure.java.io :as io]
    [clojure.string :as string]
    [clojure.tools.cli :refer [parse-opts]]
    [trptcolin.versioneer.core :as version])
  (:gen-class))

(def
  ^{:doc "Specification to define the command line options available."}
  cli-spec [["-v" "--version" "Version of this application"]
            ["-h" "--help" "Prints this help message"]])

(defn cli-help-msg
  "Render help message to write to command line. Accepts a string containing the available command line options and
  returning a string with complete help message."
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
  "Render version message. This works from both the command line running jar and in REPL."
  []
  (str "HipsCli" " " (version/get-version "io.xorshift" "hips-cli")))


(defn- cli-error-msg
  "Render an error message to write to the command line. Accepts a vector of error message strings and returning a
  string."
  [errors]
  (->> ["The following errors occurred while parsing your command:"
        ""
        (string/join \newline errors)]
       (string/join \newline)))

(defn cli-parse-command
  "Parse command line arguments. Accepts vector of arguments received from the command line and returns a map
  containing: arguments exit-message, and ok?. The value for arguments contains the set of files to read. The value for
  exit-message contains one of help message, version message, or an error message due to command line parse errors. The
  value for ok? is used with exit-message to indicate if the exit is requested as result oof an error or not. When
  displaying help or version message this value is true otherwise it is false."
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
  "Exit application with message msg and exit code status. Primary use is for help and version messages and command
  line parsing errors."
  [msg status]
  (println msg)
  (System/exit status))

(defn read-files
  "Read one or more files of person records, convert each record to a person map, and add to ppl atom. If any of the
  specified files is not readable an warning message is written to stdout and the process continues until there are no
  files to read."
  [files ppl]
  (doseq [f files]
    (if (.canRead (io/file f))
      (with-open [rdr (io/reader f)]
        (doseq [line (line-seq rdr)]
          (swap! ppl conj (person/from-csv line))))
      (prn "Cannot read file, ignoring:" (str "'" f "'")))))

(defn write-sorted-list [caption seq]
  "Write the result of a sort contained in seq along with caption to stdout."
  (println caption)
  (doseq [p seq]
    (print (str (person/to-csv p) \newline)))
  (println)
  )

(defn write-sorts
  "Sort and write person records contained in an atom. The atom is expected to contain a vector of person maps."
  [ppl]
  (write-sorted-list "OUTPUT 1 - SORTED BY GENDER AND THEN BY LAST NAME ASCENDING" (person/sort-by-gender @ppl))
  (write-sorted-list "OUTPUT 2 - SORTED BY BIRTH DATE ASCENDING" (person/sort-by-date-of-birth @ppl))
  (write-sorted-list "OUTPUT 3 - SORTED BY LAST NAME DESCENDING" (person/sort-by-last-name @ppl)))

(defn merge-and-sort
  "Command that implements reading, merging, sorting, and outputing sorted results. Excepts a vector of one or more
  file paths to files contain person records."
  [files]
  (let [ppl (atom [])]
    (read-files files ppl)
    (write-sorts ppl)))

(defn -main
  "Merge one or more files containing person records and output the result of three sorts: by gender and last name in
  ascending order, by date of birth in ascending order, and last name in descending order. There are two options
  available: --help which displays a help message and --version which displays a version message.

  Files may be delimited using comma, pipe, or space. The field values may not contain the delimiter used. The field
  values should not be quoted. The following fields in order are required for each record in the file: first-name,
  last-name, gender, favorite-color, date-of-birth. The date-of-birth field is expected to have the format yyyy-MM-dd."
  [& args]
  (let [{:keys [arguments exit-message ok?]} (cli-parse-command args)]
    (if exit-message
      (exit exit-message (if ok? 0 1))
      (merge-and-sort arguments))))
