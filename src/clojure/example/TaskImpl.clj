(ns example.TaskImpl
  (:gen-class
    :implements [example.Task]
    :init init))

(defn -init [] (println "Done"))