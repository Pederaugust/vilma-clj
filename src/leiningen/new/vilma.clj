(ns leiningen.new.vilma
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "vilma"))

(defn vilma
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' vilma project.")
    (->files data
             [".gitignore" (render "gitignore" data)]
             ["README.md" (render "README.md" data)]
             ["shadow-cljs.edn" (render "shadow-cljs.edn" data)]
             ["package.json" (render "package.json" data)]
             ["project.clj" (render "project.clj" data)]
             ["src/clj/{{sanitized}}/core.clj" (render "src/clj/core.clj" data)]
             ["src/clj/{{sanitized}}/routes.clj" (render "src/clj/routes.clj" data)]
             ["src/clj/{{sanitized}}/view/layout.clj" (render "src/clj/view_layout.clj" data)]
             ["src/clj/{{sanitized}}/model/user.clj" (render "src/clj/model/user.clj" data)]
             ["src/clj/{{sanitized}}/auth.clj" (render "src/clj/auth.clj" data)]
             ["src/clj/{{sanitized}}/controller/user.clj" (render "src/clj/controller/user.clj" data)]
             ["src/clj/{{sanitized}}/db.clj" (render "src/clj/db.clj" data)]
             ["src/cljc/{{sanitized}}/core.cljc" (render "src/cljc/core.cljc" data)]
             ["src/cljs/{{sanitized}}/core.cljs" (render "src/cljs/core.cljs" data)]
             ["src/cljs/{{sanitized}}/events.cljs" (render "src/cljs/events.cljs" data)]
             ["src/cljs/{{sanitized}}/subs.cljs" (render "src/cljs/subs.cljs" data)]
             ["src/cljs/{{sanitized}}/db.cljs" (render "src/cljs/db.cljs" data)]
             ["resources/migrations/20111206154000-initialize-db.up.sql" (render "resources/migrations/20111206154000-initialize-db.up.sql" data)]
             ["resources/migrations/20111206154000-initialize-db.down.sql" (render "resources/migrations/20111206154000-initialize-db.down.sql" data)]
             ["resources/migrations/init.sql" (render "resources/migrations/init.sql" data)]
             ["config/prod/config.edn" (render "config/prod_config.edn" data)]
             ["config/dev/config.edn" (render "config/dev_config.edn" data)])))
