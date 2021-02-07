(ns {{name}}.view.user
    (:require [{{name}}.view.layout :refer [layout]]
              [hiccup.form :refer [form-to text-field password-field submit-button]]
              [ring.util.anti-forgery :refer [anti-forgery-field]]))



(defn new []
    (layout [:div
              (form-to [:post "/registration"]
                (anti-forgery-field)
                (text-field "email")
                (password-field "password")
                (submit-button "Create User"))]))
