# Function functions

Two of Clojure’s functions, **apply** and **partial**, might seem especially weird because they both accept and return functions.

## apply

**apply** explodes a seqable data structure so it can be passed to a function that expects a rest parameter.

```clj
(max 0 1 2)
; => 2

(max [0 1 2])
; => [0 1 2]

(apply max [0 1 2])
; => 2
```

We can also define **into** in terms of **conj** by using **apply**.

```clj
(defn my-into
  [target additions]
  (apply conj target additions))

(my-into [0] [1 2 3])
; => [0 1 2 3]
```

## partial

**partial** takes a function and any number of arguments. It then returns a new function. When you call the returned function, it calls the original function with the original arguments you supplied it along with the new arguments.

```clj
(def add10 (partial + 10))
(add10 3) 
; => 13
(add10 5) 
; => 15

(def add-missing-elements
  (partial conj ["water" "earth" "air"]))

(add-missing-elements "unobtainium" "adamantium")
; => ["water" "earth" "air" "unobtainium" "adamantium"]
```

When you call **add10**, it calls the original function and arguments `(+ 10)` and tacks on whichever arguments you call **add10** with.

```clj
(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into args more-args))))

(def add20 (my-partial + 20))
(add20 3) 
; => 23
```

In general, you want to use partials when you find you’re repeating the same combination of function and arguments in many different contexts.

## complement

It’s so common to want the complement (the negation) of a Boolean function that there’s a function, **complement**, for that.

```clj
(defn identify-humans
  [social-security-numbers]
  (filter #(not (vampire? %))
          (map vampire-related-details social-security-numbers)))

(def not-vampire? (complement vampire?))
(defn identify-humans
  [social-security-numbers]
  (filter not-vampire?
          (map vampire-related-details social-security-numbers)))

(defn my-complement
  [fun]
  (fn [& args]
    (not (apply fun args))))

(def my-pos? (complement neg?))
(my-pos? 1)  
; => true

(my-pos? -1) 
; => false

```