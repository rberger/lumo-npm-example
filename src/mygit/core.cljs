(ns mygit.core
  (:require [cljs.core.async :as async])
  (:require-macros [cljs.core.async.macros :as async-macros]))

(def git ((js/require "simple-git"))) ;; The double parens around the js/require is needed!
(def res-chan (async/chan))
(defn get-tags []
  (.tags git  (fn [err, tgs] (async-macros/go (async/>! res-chan tgs)))))


(defn -main []
  (async-macros/go
    (get-tags)
    (println "RESULTS: " (js->clj (.-all (async/<! res-chan))))))

;;  (.tags git  (fn [err, tgs] (println "Yay; " (js->clj(.-all tgs))))))

;; (defonce gitlatest (node/require "git-latest-semver-tag"))
;; (println "gitlatest: " gitlatest)
;; (defn doit []
;;   (println "Doing it")
;;   (gitlatest (fn [err,tag] (println "err: " error " tags: " tag))))

;; (defn -main []
;;   (doit))

