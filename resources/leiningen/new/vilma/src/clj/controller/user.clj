(ns {{name}}.controller.user
    (:require [{{name}}.auth :as auth]
              [{{name}}.model.user :as model]
              [{{name}}.view.user :as view]
              [clojure.tools.logging :as log]))

(defn signup [{:keys [params]}]
  (if (auth/new-user! params)
    {:status 200
     :body "User Created"}
    {:status 402
     :body "There was a problem"}))

{{=<% %>=}}
(defn login [{{:keys [email password]} :params}]
  (try
    (let [user (model/user-from-email email)]
      (when (and user password
               (auth/valid-password? email password))
        (auth/sign-user (:id user))))
    (catch Exception e (log/error e))))
<%={{ }}=%>

(defn new-user [_]
  {:status 200
   :body (view/new)})
