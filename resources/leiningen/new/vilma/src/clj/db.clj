(ns {{name}}.db
  (:require [config.core :refer [env]]
            [next.jdbc :as jdbc]))

(def db-env (:db env))
(def ds (jdbc/get-datasource db-env))
