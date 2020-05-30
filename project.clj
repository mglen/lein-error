(defproject lein-error "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :source-paths      ["src/clojure"]
  :java-source-paths ["src/java"]
  :profiles {:precomp {:java-source-paths ^:replace ["src/pre/java"]}}
  :aot :all)

