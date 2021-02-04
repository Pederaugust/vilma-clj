(ns {{name}}.core
    (:gen-class)
    (:use [org.httpkit.server :only [run-server]])
    (:require
     [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
     [config.core :refer [env]]
     [clojure.tools.logging :as log]
     [ring.middleware.cors :refer [wrap-cors]]
     [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
     [ring.middleware.logger :as logger]
     [ring.middleware.reload :refer [wrap-reload]]
     [ring.middleware.resource :refer [wrap-resource]]
     [{{name}}.auth :as auth]
     [{{name}}.routes :as routes]))

(def app
  (-> routes/app-routes
      (wrap-authentication ,,, auth/backend)
      (wrap-authorization ,,, auth/backend)
      (wrap-resource "public")
      (wrap-defaults ,,, site-defaults)
      logger/wrap-with-logger
      ring.middleware.keyword-params/wrap-keyword-params
      ring.middleware.params/wrap-params
      (wrap-cors :access-control-allow-methods [:get :put :post :options :delete]
                 :access-control-allow-credentials true
                 :access-control-allow-origin [#"^(http(s)?://)?localhost:(\d){4}"])))

(def port (:port env))
(defonce server (atom nil))

(defn stop-server []
  (when-not (nil? @server)
    ;; graceful shutdown: wait 100ms for existing requests to be finished
    ;; :timeout is optional, when no timeout, stop immediately
    (println "Server Shutting down")
    (@server :timeout 100)
    (reset! server nil)))

(defn -main [& args] ;; entry point, lein run
  "Http-kit specific config/entrypoint"
  (println "Running server")
  (let [app (wrap-reload #'app)]
    (reset! server (run-server app {:port port}))))

(defn start-server []
  (-main))
