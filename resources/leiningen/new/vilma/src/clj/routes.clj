(ns {{name}}.routes
    (:require [reitit.ring :as reit]
              [reitit.ring.coercion :as coercion]
              [reitit.ring.coercion :as coercion]
              [reitit.ring.middleware.muuntaja :as muuntaja]
              [reitit.ring.middleware.exception :as exception]
              [reitit.ring.middleware.multipart :as multipart]
              [reitit.ring.middleware.parameters :as params]
              [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
              [ring.middleware.cors :refer [wrap-cors]]
              [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
              [ring.middleware.logger :as logger]
              [ring.middleware.resource :refer [wrap-resource]]
              [{{name}}.auth :as auth]
              [{{name}}.controller.user :as user]
              [{{name}}.controller.main :as main]))

(defn not-found-handler [_]
  {:status 404
   :body "NOT FOUND"})

(def router
  (reit/router
   [["/" {:get main/index}]
    ["/session" {:post user/login}]
    ["/registration" {:post user/signup
                      :get user/new-user}]]
   {:data {:middleware [#(wrap-authentication % auth/backend)
                        #(wrap-authorization % auth/backend)
                        #(wrap-resource % "public")
                        logger/wrap-with-logger
                        params/parameters-middleware
                        muuntaja/format-negotiate-middleware
                        ;; encoding response body
                        muuntaja/format-response-middleware
                        ;; exception handling
                        exception/exception-middleware
                        ;; decoding request body
                        muuntaja/format-request-middleware
                        ;; coercing response bodys
                        coercion/coerce-response-middleware
                        ;; coercing request parameters
                        coercion/coerce-request-middleware
                        ;; multipart
                        multipart/multipart-middleware]}}))

(def handler
  (reit/ring-handler router
                     (constantly {:status 404 :body "NOT FOUND"})))
