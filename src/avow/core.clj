(ns avow.core
  (:require [clojure.core.protocols :as pro])
  (:import (java.util Set Map)
           (java.util.regex Pattern)
           (clojure.lang Fn Sequential)))

(when-not (extends? pro/IKVReduce Map)
    (extend-protocol pro/IKVReduce
      Map
      (kv-reduce [amap f init]
        (reduce (fn [agg [k v]] (f agg k v)) init amap))))

(defprotocol Avowable
  :extend-via-metadata true
  (avow* [expected actual]
    "Checks equality and returns a map explaining the result."))

(defn combinator
  "Combines avow results."
  ([result]
   (if (map? result) result (apply combinator result)))
  ([result1 result2]
   {:result     (and (:result result1) (:result result2))
    :mismatches (into [] (concat (:mismatches result1 []) (:mismatches result2 [])))})
  ([result1 result2 & more]
   (reduce combinator {} (cons result1 (cons result2 more)))))

(defn avow' [expected actual]
  (let [result (avow* expected actual)]
    result))

(extend-protocol Avowable
  Object
  (avow* [expected actual]
    {:result (= expected actual)})
  nil
  (avow* [expected actual]
    {:result (= expected actual)})
  Set
  (avow* [expected actual]
    (if (empty? expected)
      {:result true}
      (let [cp      (for [e expected a actual] [e a])
            results (map #(hash-map :pair %1 :avow (apply avow' %)) cp)
            matches (filter (comp :result :avow) results)]
        {:result (and (= (count matches) (count expected))
                      (apply distinct? (map (comp first :pair) matches))
                      (apply distinct? (map (comp second :pair) matches)))})))
  Pattern
  (avow* [expected actual]
    {:result
     (if (instance? Pattern actual)
       (= (str expected) (str actual))
       (some? (re-find expected (str actual))))})
  Number
  (avow* [expected actual]
    {:result (and (some? actual) (== expected actual))})
  Fn
  (avow* [expected actual]
    {:result (or (identical? expected actual) (expected actual))})
  Sequential
  (avow* [expected actual]
    (let [result (avow' (count expected) (count actual))]
      (if-not (:result result)
        result
        (apply combinator (map avow' expected actual)))))
  Map
  (avow* [expected actual]
    (reduce-kv
      (fn [agg k v]
        (combinator agg (avow' v (get actual k))))
      {:result true}
      expected)))

(defn avow [expected actual]
  (boolean (:result (avow' expected actual))))