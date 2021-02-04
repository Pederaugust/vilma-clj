(ns {{name}}.controller.user
    (:require [{{name}}.auth :as auth]
              [{{name}}.model.user :as model]
              [clojure.tools.logging :as log]))

(defn signup [{:keys [params]}]
  (auth/new-user! params)
  {:status 200
   :body "User Created"})

{{=<% %>=}}
(defn login [{{:keys [email password]} :params}]
  (try
    (let [user (model/user-from-email email)]
      (when (and user password
               (auth/valid-password? email password))
        (auth/sign-user (:id user))))
    (catch Exception e (log/error e))))

<%={{ }}=%>
