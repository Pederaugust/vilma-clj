(ns {{name}}.controller.main
  (:require [{{name}}.view.layout :as view]))

(defn index [_]
  {:status 200
   :body (view/layout [:div
                       [:h1 "Welcome to Vilma"]
                       [:div#app]])})
