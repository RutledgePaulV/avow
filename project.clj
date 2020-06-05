(defproject org.clojars.rutledgepaulv/avow "0.1.1-SNAPSHOT"

  :description
  "An open definition of equality for more concise and robust tests."

  :url
  "https://github.com/rutledgepaulv/avowable"

  :license
  {:name "MIT" :url "http://opensource.org/licenses/MIT" :year 2020}

  :scm
  {:name "git" :url "https://github.com/rutledgepaulv/avowable"}

  :deploy-repositories
  [["releases" :clojars]
   ["snapshots" :clojars]]

  :dependencies
  [[org.clojure/clojure "1.10.1"]]

  :plugins
  [[lein-cloverage "1.1.2"]]

  :repl-options
  {:init-ns avow.core})
