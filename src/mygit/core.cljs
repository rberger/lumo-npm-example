(ns mygit.core
  (:require [cljs.nodejs :as node]))
   ;;          [clojure.core.async]
   ;;          [cljs-asynchronize])
   ;; (:require-macros [cljs-asynchronize.macros :as dm :refer [asynchronize]]
;;                  [cljs.core.async.macros :as am :refer [go]]))

(node/enable-util-print!)

(defonce gitlatest (node/require "git-latest-semver-tag"))
(println "gitlatest: " gitlatest)
(defn doit []
  (println "Doing it")
  (js->clj (gitlatest (fn [err,tag] (println "tags: " tag)))))

(defn -main []
  (doit))

;; (def git (node/require "simple-git" "."))
;; (println "git: " git)

;; (defn ^:export mycallback [err, tgs]
;;   (println "TAGS: " tgs))

;; (defn tags [cb]
;;    (def result (. git tags (fn [err, tgs] (println "Yay; " tgs) )))
;;    (println "FOO: " result))

;; (defn -main []
;;   (tags mycallback))




