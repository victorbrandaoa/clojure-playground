# Core Functions

## Programming to Abstractions

In programming, indirection is a generic term for the mechanisms a language employs so that one name can have multiple, related meanings. For instance, you can use the functions **first**, **rest** and **cons** with any data structure. Indirection is what makes abstraction possible.

Polymorphism is one way that Clojure provides indirection. Polymorphic functions dispatch to different function bodies based on the type of the argument supplied.

## Seq Functions

### map

You’ve seen many examples of **map** by now, but this section shows **map** doing two new tasks: taking multiple collections as arguments and taking a collection of functions as an argument.

```clj
(map str ["a" "b" "c"] ["A" "B" "C"])
; => ("aA" "bB" "cC")
```

When you pass **map** multiple collections, the elements of the first collection `(["a" "b" "c"])` will be passed as the first argument of the mapping function `(str)`, the elements of the second collection `(["A" "B" "C"])` will be passed as the second argument, and so on.

OBS: Just be sure that your mapping function can take a number of arguments equal to the number of collections you’re passing to **map**.

Another fun thing you can do with map is pass it a collection of functions. You could use this if you wanted to perform a set of calculations on different collections of numbers, like so:

```clj
(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(stats [3 4 10])
; => (17 3 17/3)

(stats [80 1 44 13 6])
; => (144 5 144/5)
```

In this example, the **stats** function iterates over a vector of functions, applying each function to numbers.

Additionally, Clojurists often use **map** to retrieve the value associated with a keyword from a collection of map data structures.

```clj
(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(map :real identities)
; => ("Bruce Wayne" "Peter Parker" "Your mom" "Your dad")
```

### reduce

You can use **reduce** to transform a map’s values, producing a new map with the same keys but with updated values.

```clj
(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10})
; => {:max 31, :min 11}
```

Another use for **reduce** is to filter out keys from a map based on their value.

```clj
(reduce (fn [new-map [key val]]
          (if (> val 4)
            (assoc new-map key val)
            new-map))
        {}
        {:human 4.1
         :critter 3.9})
; => {:human 4.1}
```

The takeaway here is that **reduce** is a more flexible function than it first appears. Whenever you want to derive a new value from a seqable data structure, reduce will usually be able to do what you need.

### take, drop, take-while, and drop-while

**take** and **drop** both take two arguments: a number and a sequence. **take** returns the first n elements of the sequence, whereas **drop** returns the sequence with the first n elements removed.

```clj
(take 3 [1 2 3 4 5 6 7 8 9 10])
; => (1 2 3)

(drop 3 [1 2 3 4 5 6 7 8 9 10])
; => (4 5 6 7 8 9 10)
```

**take-while** and **drop-while** are a bit more interesting. Each takes a predicate function (a function whose return value is evaluated for truth or falsity) to determine when it should stop taking or dropping.

```clj
(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(take-while #(< (:month %) 3) food-journal)
; => ({:month 1 :day 1 :human 5.3 :critter 2.3}
; =>  {:month 1 :day 2 :human 5.1 :critter 2.0}
; =>  {:month 2 :day 1 :human 4.9 :critter 2.1}
; =>  {:month 2 :day 2 :human 5.0 :critter 2.5})

(drop-while #(< (:month %) 3) food-journal)
; => ({:month 3 :day 1 :human 4.2 :critter 3.3}
; =>  {:month 3 :day 2 :human 4.0 :critter 3.8}
; =>  {:month 4 :day 1 :human 3.7 :critter 3.9}
; =>  {:month 4 :day 2 :human 3.7 :critter 3.6})

;; In the following example we use both methods together
(take-while #(< (:month %) 4)
            (drop-while #(< (:month %) 2) food-journal))
; => ({:month 2 :day 1 :human 4.9 :critter 2.1}
; =>  {:month 2 :day 2 :human 5.0 :critter 2.5}
; =>  {:month 3 :day 1 :human 4.2 :critter 3.3}
; =>  {:month 3 :day 2 :human 4.0 :critter 3.8})
```

### filter and some

Use filter to return all elements of a sequence that test true for a predicate function.

```clj
(filter #(< (:human %) 5) food-journal)
; => ({:month 2 :day 1 :human 4.9 :critter 2.1}
; =>  {:month 3 :day 1 :human 4.2 :critter 3.3}
; =>  {:month 3 :day 2 :human 4.0 :critter 3.8}
; =>  {:month 4 :day 1 :human 3.7 :critter 3.9}
; =>  {:month 4 :day 2 :human 3.7 :critter 3.6})
```

You might be wondering why we didn’t just use **filter** in the **take-while** and **drop-while** examples earlier. Indeed, **filter** would work for that too. This use is perfectly fine, but **filter** can end up processing all of your data, which isn’t always necessary. Because the food journal is already sorted by date, we know that **take-while** will return the data we want without having to examine any of the data we won’t need. Therefore, **take-while** can be more efficient.

Often, you want to know whether a collection contains any values that test true for a predicate function. The **some** function does that, returning the first truthy value.

```clj
(some #(> (:critter %) 5) food-journal)
; => nil

(some #(> (:critter %) 3) food-journal)
; => true
```

Notice that the return value in the second example is **true** and not the actual entry that produced the true value. The reason is that the anonymous function `#(> (:critter %) 3)` returns **true** or **false**.

Here, a slightly different anonymous function uses and to first check whether the condition `(> (:critter %) 3)` is **true**, and then returns the entry when the condition is indeed **true**.

```clj
(some #(and (> (:critter %) 3) %) food-journal)
; => {:month 3 :day 1 :human 4.2 :critter 3.3}
```

### sort and sort-by

You can sort elements in ascending order with sort.

```clj
(sort [3 1 2])
; => (1 2 3)
```

If your sorting needs are more complicated, you can use **sort-by**, which allows you to apply a function (sometimes called a key function) to the elements of a sequence and use the values it returns to determine the sort order.

```clj
(sort-by count ["aaa" "c" "bb"])
; => ("c" "bb" "aaa")
```

### concat

Finally, **concat** simply appends the members of one sequence to the end of another.

```clj
(concat [1 2] [3 4])
; => (1 2 3 4)
```
