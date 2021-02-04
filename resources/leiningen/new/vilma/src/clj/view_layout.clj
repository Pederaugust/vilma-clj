(ns {{name}}.view.layout
   (:require [hiccup.page :refer [html5 include-css include-js]]))


(defn layout
  ([content] (layout nil content))
  ([head-content content]
   (html5 {:lang "en"}
    [:head [:meta {:charset "UTF-8"}]
        [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
      (when head-content head-content)]
    [:body content]
    (include-js "/js/main.js")
    [:script "{{name}}.core.init();"])))
