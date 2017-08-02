(ns mygit.core)
  ;; (:require [clojure.string :as str]
  ;;           [cljs.tools.cli :refer [parse-opts]]))

(def git (js/require "simple-git"))

(defn tags []
  (println (.-tags git)))

(defn -main []
  (tags))




