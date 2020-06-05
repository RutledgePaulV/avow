(ns avow.core
  (:import (java.util Set Map)
           (java.util.regex Pattern)
           (clojure.lang Fn MapEntry IPersistentVector ISeq)))

(defprotocol Avowable
  :extend-via-metadata true
  (avow* [expected actual]
    "Checks if actual matches the expected pattern and returns a boolean result."))

(defn avow
  ([expected actual]
   (avow* expected actual))
  ([expected actual & more-actuals]
   (->> (cons actual more-actuals)
        (reduce #(or (avow expected %2) (reduced false)) true))))

(extend-protocol Avowable
  Object
  (avow* [expected actual]
    (= expected actual))
  nil
  (avow* [expected actual]
    (= expected actual))
  Set
  (avow* [expected actual]
    (or (empty? expected) (boolean (some #(avow % actual) expected))))
  Pattern
  (avow* [expected actual]
    (if (instance? Pattern actual)
      (= (str expected) (str actual))
      (some? (re-find expected (str actual)))))
  Number
  (avow* [expected actual]
    (if (number? actual) (== expected actual) false))
  Fn
  (avow* [expected actual]
    (or (identical? expected actual) (expected actual)))
  ISeq
  (avow* [expected actual]
    (every? boolean (map avow expected actual)))
  IPersistentVector
  (avow* [expected actual]
    (if (== (count expected) (count actual))
      (every? boolean (map avow expected actual))
      false))
  MapEntry
  (avow* [[k v] actual]
    (cond
      (ifn? k) (avow v (k actual))
      (map? actual) (avow v (get actual k))
      :otherwise false))
  Map
  (avow* [expected actual]
    (reduce #(or (avow %2 actual) (reduced false)) true expected)))