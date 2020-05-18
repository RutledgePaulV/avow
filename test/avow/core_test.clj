(ns avow.core-test
  (:require [clojure.test :refer :all]
            [avow.core :refer :all]))

(deftest subsets-of-map-keys
  (are [pred expected actual] (pred (avow expected actual))
    true? {:a 1} {:a 1 :b 2}
    true? {} {:a 1 :b 2}
    false? {:a 1} {:a 2 :b 2}
    false? {:a 1} {}))

(deftest differing-numerical-types
  (are [pred expected actual] (pred (avow expected actual))
    true? 1 1.0
    true? 1.0 1
    true? 1N 1.0
    true? 1.0 1N
    true? (float 1) 1
    true? 1 (float 1)
    true? (int 1) (long 1)
    true? (long 1) (int 1)))

(deftest positional-predicates
  (are [pred expected actual] (pred (avow expected actual))
    true? odd? 1
    true? even? 2
    true? {:a odd?} {:a 1 :b 2}
    false? {:a odd?} {:a 2 :b 1}
    true? {:a odd? :b even?} {:a 1 :b 2}
    false? {:a even? :b odd?} {:a 1 :b 2}
    true? [even?] [2]
    false? [even?] [1]))



