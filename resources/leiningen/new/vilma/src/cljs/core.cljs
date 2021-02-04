(ns {{name}}.core
    (:require [{{name}}.events]
              [{{name}}.subs]
              [re-frame.core :as rf]
              [reagent.dom :as rd]))


;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (js/console.log "start"))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  ;;
  (js/console.log "init")
  (when (.getElementById js/document "app")
    (rf/dispatch [:init])
    (rd/render [:div [:h1 "Hello Vilma!"]]
               (.getElementById js/document "app"))))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))
