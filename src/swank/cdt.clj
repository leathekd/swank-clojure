(ns swank.cdt
  ;; convenience namespace to give users easy access to main functions
  (:refer-clojure :exclude [next])
  (:require [cdt.ui :as cdt]
            [cdt.events]
            [swank.core]
            [swank.core.cdt-backends :as cbackends]))

(def swank-cdt-release "1.5.0a")

(cdt/expose cbackends/set-catch cbackends/set-bp cbackends/reval
            cdt/delete-catch cdt/delete-bp cdt/delete-all-breakpoints
            cdt/bg cdt/delete-all-catches)

(defn print-bps []
  (swank.core/send-to-emacs
   `(:write-string
     ~(apply str "\nCDT Breakpoints:\n"
             (for [[n k] (keep-indexed vector (keys @cdt.events/bp-list))]
               (format "%s: %s\n" n k)))
     :repl-result)))

(defn print-catches []
  (swank.core/send-to-emacs
   `(:write-string
     ~(apply str "\nCDT Caught Exceptions:\n"
             (for [[n k] (keep-indexed vector (keys @cdt.events/catch-list))]
               (format "%s: %s\n" n k)))
     :repl-result)))

(cbackends/cdt-backend-init swank-cdt-release)
