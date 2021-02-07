(ns {{name}}.core
    (:gen-class)
    (:use [org.httpkit.server :only [run-server]])
    (:require
     [config.core :refer [env]]
     [clojure.tools.logging :as log]
     [ring.middleware.reload :refer [wrap-reload]]
     [{{name}}.routes :as routes]))

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
  (let [app (wrap-reload #'routes/handler)]
    (reset! server (run-server app {:port port}))))

(defn start-server []
  (-main))
