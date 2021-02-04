(ns {{name}}.routes
    (:require [compojure.core :refer :all]
              [compojure.route :as route]
              [{{name}}.view.layout :as view]
              [{{name}}.controller.user :as user]))


(defroutes app-routes
  (GET "/" req (view/layout [:div#app]))
  (POST "/session" req (user/login req))
  (POST "/registration" req (user/signup req))
  (route/not-found "Oops! Something went wrong!"))
