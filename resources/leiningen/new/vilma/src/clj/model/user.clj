(ns {{name}}.model.user
    (:require [{{name}}.db :refer [ds]]
              [clojure.string :as string]
              [next.jdbc :as jdbc]
              [honeysql.core :as sql]))


(defn user-from-email [email]
  (jdbc/execute! ds (sql/format {:select :*
                                 :from :users
                                 :where [:= :u.email (string/lower-case email)]})))
