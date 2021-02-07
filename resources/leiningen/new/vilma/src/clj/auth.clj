(ns {{name}}.auth
  (:require
   [buddy.auth.backends :as backends]
   [buddy.core.hash :as hash]
   [buddy.core.crypto :as crypto]
   [buddy.core.nonce :as nonce]
   [buddy.core.codecs :as codecs]
   [buddy.sign.jwt :as jwt]
   [clojure.tools.logging :as log]
   [clojure.string :as string]
   [config.core :refer [env]]
   [next.jdbc :as jdbc]
   [next.jdbc.sql :as sql]
   [next.jdbc.result-set :as rs]
   [{{name}}.model.user :as model]
   [{{name}}.db :refer [ds]]))

;Authorization/Authentication
(def secret-key
  (:secret-key (:authentication env)))
;
;Password hashing and strategies
(def password-secret-key
  (hash/sha512
   (:secret-key
    (:hashing env))))

(def strategy
  {:algorithm :aes256-cbc-hmac-sha512})

(defn sign-user [userid]
  (jwt/sign {:user userid} secret-key))

(defn unsign-user [token]
  (try
    (jwt/unsign token secret-key)
    (catch Exception e
      (do (log/error (.getMessage e))
          (.getMessage e)))))

(defn logged-in-user [cookies]
  (if-let [token (get cookies "token")]
    (:user (unsign-user (:value token)))
    false))

(def backend (backends/jws {:secret secret-key}))


(defn generate-salt []
  (nonce/random-bytes 16))

(defn encrypt-password [password salt]
  (let [bytes (codecs/to-bytes password)]
    (-> (crypto/encrypt bytes password-secret-key salt strategy)
        codecs/bytes->hex)))

(defn verify-password [password hex salt]
  (= (encrypt-password password salt) hex))

(defn valid-password? [email password]
  (let [user (model/user-from-email email)]
    (verify-password password
                     (:passwordhash user) ; Hash
                     (codecs/hex->bytes (:passwordsalt user))))) ; Salt

(defn new-user! [new-user]
  "Creates a new user"
  (if-not (model/user-from-email (:email new-user))
    (let [salt (generate-salt)
          hash (encrypt-password (:password new-user) salt)
          user (-> new-user
                   (assoc ,,, :passwordsalt (codecs/bytes->hex salt))
                   (assoc ,,, :passwordhash hash)
                   (dissoc ,,, :password))]
      (sql/insert! ds :users user))
    false))
