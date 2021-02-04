# Vilma
Vilma is a lightweight starter to get you up and running with the basic stuff in clojure and clojurescript.

*So whats on the menu?*

### Backend
+ The backend uses migratus for migrations
+ Buddy-auth for authorization and authentication (comes with pre-built usermodel and auth so you don't have to do aything more than configure)
+ Vilma has good defaults for the clojure/server kind of stuff, model-view-controller to separate concerns
+ Vilma uses compojure for routing, http-kit as a server (making it easy to use libraries such as sente to get some realtime features), and the excelent library/DSL hiccup for most of the backend work. Meaning no silly templating language, only good ol' clojure DSLs for most of the heavy lifting.
+ For testing I recommend midje as a good alternative

### Frontend
+ The frontend uses reagent for react-type code
+ The absolutely fantastic **re-frame** framework for sane defaults and beautifuly designed frontend code. (in my opinion one of the best designed libraries)
+ Lastly the frontend uses the excelent shadow-cljs compiler/builder for all the frontend code

*Bon Apetit*

# Usage

First, let's do some initialization.

``` sh
lein new vilma *my-project*
```

``` sh
cd *my-project*
```

``` sh
npm install
```

Create a development database in postgresql:
(the specs of this database need to be input into config/dev/config.edn, and [project.clj :profiles :migratus])
The standard is to use your app-name followed by dev as a database name. (this might not always work with "-" so pay special consideration)
{{name}}_dev

``` sh
lein migratus create
```

## Backend tutorial
Separating concerns is important. Therefore I will provide a small tutorial for how to create a simple blog post and view it.

Let's begin by going into the clojure realm of our code. You'll find it in:

``` sh
src/clj/(my-project)
```

You will have three important folders to consider:

``` sh
.
(other folders and files)
├── model
│   └── user.clj
└── view
    └── layout.clj
├── controller
│   └── user.clj
```

Model, view and controller. We already have som files here for our user model.
Let's add a new model-file, call it model/post.clj.

Inside model.post we will create a few functions, create-post, and get-post.

``` clojure
(ns my-project.model.post
    (:require [my-project.db :refer [ds]]
              [next.jdbc :as jdbc]
              [next.jdbc.helpers :as helpers]
              [honeysql.core :as sql]
              [honeysql.helpers :refer [insert-into values select from where]]))
              
(defn create-post [post]
    (helpers/insert! ds 
                    (sql/format (-> (insert-into :posts)
                                    (values [post])))))

(defn get-post [id]
    (jdbc/execute-one! ds 
                        (sql/format (-> (select :*)
                                        (from :posts)
                                        (where [:= :p.id id])))))
```
This is a simple approach to model building, improvements should be made with spec (remember to create spec files in the cljc folder to make them available on the frontend too)
Remember to create and execute migrations (see migratus documentation, in this example we need three fields; id, title, content).
Next up we will create a controller for our post. Create a controller/post.clj file.

``` clojure
(ns my-project.controller.post
    (:require [my-project.model.post :as model]
                [my-project.view.post :as view]))
             
(defn create [{post :params}]
    (->> (model/create-post post)
        :id
        (str "/post/" ,,)
        redirect))

(defn show [{id :params}]
    (->> (model/get-post id)
        view/show))
        
(defn new [req]
    (view/new))
```

Now we need to create some routes and views for these functions.
Lets create the views first. (view/post.clj)

``` clojure
(ns my-project.view.post
    (:require [my-project.view.layout :refer [layout]]
                [hiccup.form :refer [form-to text-field text-area submit-button]]
                [ring.util.anti-forgery :refer [anti-forgery-field]]))
    
(defn show [post]
    (layout [:div
              [:h1 (:title post)]
              [:p (:content post)]]))

(defn new []
    (layout [:div
              (form-to [:post "/post"]
                (anti-forgery-field)
                (text-field "title")
                (text-area "content")
                (submit-button "create post"))]))
```

And lastly we will use compojure for our routes, the file is called routes.clj and should look something like this:

``` clojure
(ns my-project.routes
    (:require [compojure.core :refer :all]
              [compojure.route :as route]
              [my-project.view.layout :as view]
              [my-project.controller.post :as post]
              [my-project.controller.user :as user]))


(defroutes app-routes
  (GET "/" req (view/layout [:div#app]))
  (GET "/post/new" req (post/new req))
  (POST "/post" req (post/create req))
  (GET "/post/:id" req (post/show req))
  (POST "/session" req (user/login req))
  (POST "/registration" req (user/signup req))
  (route/not-found "Oops! Something went wrong!"))

```

And that's it. Lots of small improvements can be made, with validation, and extending posts to be related to users.

## Migrations

Vilma uses migratus to run and make migrations.

Create a migration:

``` sh
lein migratus create add-something-table
```

Migrations can be located in the resources/migrations folder with two timestamped sql files.

When you are happy, migrate:

``` sh
lein migratus migrate
```


## To run the app

Start the frontend server:

``` sh
shadow-cljs watch app
```

Start the backend server:

``` sh
lein run
```

Simple stuff. Now you have a fully functioning server.


## Before Deploy

release the shadow-cljs app:

``` sh
shadow-cljs release app
```

For all else look into how to release a leiningen app.



## License

Copyright © 2021 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
