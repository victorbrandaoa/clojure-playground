# Let expression

**let** binds names to values.

```clj
(let [x 3]
  x)
; => 3

(def dalmatian-list
  ["Pongo" "Perdita" "Puppy 1" "Puppy 2"])
(let [dalmatians (take 2 dalmatian-list)]
  dalmatians)
; => ("Pongo" "Perdita")

(def x 0)
(let [x 1] x)
; => 1
```

In the last example, you first bind the name x to the value 0 using **def**. Then, **let** creates a new scope in which the name x is bound to the value 1. I think of scope as the context for what something refers to. You can reference existing bindings in your let binding.

```clj
(def x 0)
(let [x (inc x)] x)
; => 1

; You can also use rest parameters in let, just like you can in functions
(let [[pongo & dalmatians] dalmatian-list]
  [pongo dalmatians])
; => ["Pongo" ("Perdita" "Puppy 1" "Puppy 2")]
```

Notice that the value of a **let** form is the last form in its body that is evaluated. **let** forms have two main uses. First, they provide clarity by allowing you to name things. Second, they allow you to evaluate an expression only once and reuse the result. This is especially important when you need to reuse the result of an expensive function call, like a network API call.

# Loop expression

**loop** provides another way to do recursion in Clojure. In the following example, it’s as if **loop** creates an anonymous function with a parameter named iteration, and **recur** allows you to call the function from within itself, passing the argument (inc iteration).

```clj
(loop [iteration 0]
  (println (str "Iteration " iteration))
  (if (> iteration 3)
    (println "Goodbye!")
    (recur (inc iteration))))
; => Iteration 0
; => Iteration 1
; => Iteration 2
; => Iteration 3
; => Iteration 4
; => Goodbye!

; You could in fact accomplish the same thing by just using a normal function definition
(defn recursive-printer
  ([]
     (recursive-printer 0))
  ([iteration]
     (println iteration)
     (if (> iteration 3)
       (println "Goodbye!")
       (recursive-printer (inc iteration)))))
(recursive-printer)
; => Iteration 0
; => Iteration 1
; => Iteration 2
; => Iteration 3
; => Iteration 4
; => Goodbye!
```

# Reduce

The pattern of process each element in a sequence and build a result is so common that there’s a built-in function for it called **reduce**.

```clj
;; sum with reduce
(reduce + [1 2 3 4])
; => 10

; reduce also takes an optional initial value
(reduce + 15 [1 2 3 4])
; => 25
```

The reduce function works according to the following steps:

1. Apply the given function to the first two elements of a sequence. That’s where (+ 1 2) comes from.

2. Apply the given function to the result and the next element of the sequence. In this case, the result of step 1 is 3, and the next element of the sequence is 3 as well. So the final result is (+ 3 3).

3. Repeat step 2 for every remaining element in the sequence.

OBS: If you provide an initial value, **reduce** starts by applying the given function to the initial value and the first element of the sequence rather than the first two elements of the sequence.