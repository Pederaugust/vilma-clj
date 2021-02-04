(defproject {{name}} "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :dependencies [[buddy "2.0.0"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [http-kit "2.3.0"]

                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/core.async "1.1.587"]
                 [org.clojure/test.check "1.0.0"]

                 [org.postgresql/postgresql "42.1.3"]
                 [org.slf4j/slf4j-simple "1.7.5"]

                 [lambdaisland/ring.middleware.logger "0.5.1"]

                 [migratus "1.3.4"]
                 [migratus-lein "0.7.3"]
                 [ring/ring-anti-forgery "1.3.0"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-devel "1.8.0"]
                 [ring-cors "0.1.13"]
                 [seancorfield/next.jdbc "1.0.409" :exclusions [org.clojure/tools.logging]]
                 [honeysql "1.0.444"]

                 [yogthos/config "1.1.6" :exclusions [org.clojure/tools.logging]]]


  :main {{name}}.core
  :plugins [[lein-ring "0.12.5"]
            [migratus-lein "0.7.3"]]

  :source-paths ["src/clj" "src/cljc"]
  :ring {:handler {{name}}.core/app}

  :profiles
  {:dev {:resource-paths ["config/dev"]
         :migratus {:store :database
                    :migration-dir "migrations"
                    :init-script "init.sql"
                    :db {:classname "org.postgresql.jdbc.Driver"
                         :subprotocol "postgresql"
                         :subname "{{name}}_dev"
                         :user "server"
                         :password "password"}}
         :dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]
                        [midje "1.9.9"]]}

   :prod {:resource-paths ["config/prod"]
          :dependencies [[javax.servlet/servlet-api "2.5"]
                         [ring/ring-mock "0.3.2"]]}})
