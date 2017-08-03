(ns mygit.core
  ;; Need the following so we can use core.async to get the callback data back
  ;; into the main program
  (:require [cljs.core.async :as async])
  (:require-macros [cljs.core.async.macros :as async-macros]))

;; Require the npm libraries. They must have already been downloaded into this repo 
;;
;; Note that `simple-git` is a function that returns an object that can then have
;; various functions chained to.
(def simple-git (js/require "simple-git")) 

;; `git-latest` is a function and does not have any sub functions or properties
(def git-latest (js/require "git-latest-semver-tag"))

(def simple-git-res-chan (async/chan))
(def git-latest-res-chan (async/chan))

(defn get-tags 
  "Use the tags function of simple-git to get all the tags in the specified
  directory/repo Its asynchronous so a callback is used to get the resutls. This
  uses core.async to get the results back into the main program"
  [directory]
  (.tags (simple-git directory)  (fn [err, tgs] (async-macros/go (async/>! simple-git-res-chan tgs)))))

(defn get-latest-semver-tag
  "Use the git-latest-semver-tag npm library to get the latest semver style tag
  of the current repo Its asynchronous so a callback is used to get the resutls.
  This uses core.async to get the results back into the main program"
  []
  (git-latest (fn [err,tag] (async-macros/go (async/>! git-latest-res-chan tag)))))

(defn -main []
  (async-macros/go
    (get-tags ".")
    (get-latest-semver-tag)
    (println "Simple-Git RESULTS: " (js->clj (.-all (async/<! simple-git-res-chan))))
    (println "Git-Latest RESULTS: " (async/<! git-latest-res-chan))))
