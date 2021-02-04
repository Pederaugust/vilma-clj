(ns {{name}}.events
    (:require [re-frame.core :as rf]
              [{{name}}.db :as db]))

(rf/reg-event-db
 :init
 (fn [_ _]
   db/initial-db))
