# Vilma Starter!

Welcome to Vilma.

First, let's do some initialization.

``` sh
npm install
```

Create a development database in postgresql:
(the specs of this database need to be input into config/dev/config.edn, and [project.clj :profiles :migratus])
The standard is to use your app-name followed by dev as a database name.
{{name}}_dev

``` sql
CREATE DATABASE {{name}}_dev;
```

``` sh
lein migratus migrate
```

That was easy.

## Migrations

Vilma uses migratus to run and make migrations.

Create a migration:

``` sh
lein migratus create add-something-table
```

Migrations can be located in the src/migrations folder with two timestamped sql files.

When you are happy, migrate:
``` sh
lein migratus create add-something-table
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

Simple stuff.


## Before Deploy

release the shadow-cljs app:

``` sh
shadow-cljs release app
```

For all else look into how to release a leiningen app.
