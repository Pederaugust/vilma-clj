(ns {{name}}.subs
    (:require [re-frame.core :as rf]))

(rf/reg-sub :get-db (fn [db _] db))
