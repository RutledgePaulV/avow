
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

### License

This project is licensed under [MIT license](http://opensource.org/licenses/MIT).