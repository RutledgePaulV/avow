[![Build Status](https://travis-ci.com/rutledgepaulv/avow.svg?branch=master)](https://travis-ci.com/rutledgepaulv/avow)
[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.rutledgepaulv/avow.svg)](https://clojars.org/org.clojars.rutledgepaulv/avow)
[![codecov](https://codecov.io/gh/rutledgepaulv/avow/branch/master/graph/badge.svg)](https://codecov.io/gh/rutledgepaulv/avow)

> avow (verb)
>
> to declare openly, bluntly, and without shame

---

### Rationale

Tests shouldn't break when you make non-breaking code changes. By using a more open
definition of equality we can achieve declarative code that focuses on the 
essence of an assertion and ignores incidentals.

---

### Usage

```clojure
(require '[avow.core :refer :all])

(def successful-response {:status 200 :body some?})

(deftest http-test
 (avow successful-response (http/get "http://my-app:3000")))

```

---

### Alternatives

[motif](https://github.com/Invocatis/motif)

I learned about motif halfway through writing Avow. I decided to write it anyway because I think
protocols are a better (open to extension) implementation decision.

---

### License

This project is licensed under [MIT license](http://opensource.org/licenses/MIT).
