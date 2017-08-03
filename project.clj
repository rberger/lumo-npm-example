;; This project file is only needed to
;; download (andare)[https://github.com/mfikes/andare] into your local maven
;; repo (usually ~/.m2) as a dependency
;;
;; Its not used by lumo or the program
;;
(defproject lumo-npm-example "0.1.1-SNAPSHOT"
  :description "Testing lumo with npm libraries simple-git and "
  :license {:name "The MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[andare "0.7.0"]
                 ;; Not sure if its really needed to require clojurescrpt if its being used with lumo
                 [org.clojure/clojurescript "1.9.854"]])
