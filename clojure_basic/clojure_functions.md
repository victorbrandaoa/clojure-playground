# Functions

## Calling functions

Remember that all Clojure operations have the same syntax: opening parenthesis, operator, operands, closing parenthesis. Function call is just another term for an operation where the operator is a function or a function expression (an expression that returns a function). This lets you write some pretty interesting code. Here’s a function expression that returns the **+** (addition) function.

```clj
(or + -)
; => #<core$_PLUS_ clojure.core$_PLUS_@76dace31>

((or + -) 1 2 3)
; => 6

((and (= 1 1) +) 1 2 3)
; => 6

((first [+ 0]) 1 2 3)
; => 6
```

Function flexibility doesn’t end with the function expression! Syntactically, functions can take any expressions as arguments, including other functions. Functions that can either take a function as an argument or return a function are called higher-order functions. Programming languages with higher-order functions are said to support first-class functions because you can treat functions as values in the same way you treat more familiar data types like numbers and vectors.

Take the map function, for instance. **map** creates a new list by applying a function to each member of a collection.

```clj
(inc 1.1)
; => 2.1

(map inc [0 1 2 3])
; => (1 2 3 4)
```

Clojure’s support for first-class functions allows you to build more power­ful abstractions than you can in languages without them.

Clojure evaluates all function arguments recursively before passing them to the function. Here’s how Clojure would evaluate a function call whose arguments are also function calls.

```clj
(+ (inc 199) (/ 100 (- 7 2)))
(+ 200 (/ 100 (- 7 2))) ; evaluated "(inc 199)"
(+ 200 (/ 100 5)) ; evaluated (- 7 2)
(+ 200 20) ; evaluated (/ 100 5)
220 ; final evaluation
```

## Defining Functions

Function definitions are composed of five main parts:

* **defn**
* Function name
* A docstring describing the function (optional)
* Parameters listed in brackets
* Function body

```clj
(defn too-enthusiastic
  "Return a cheer that might be a bit too enthusiastic"
  [name]
  (str "OH. MY. GOD! " name " YOU ARE MOST DEFINITELY LIKE THE BEST "
  "MAN SLASH WOMAN EVER I LOVE YOU AND WE SHOULD RUN AWAY SOMEWHERE"))

(too-enthusiastic "Zelda")
; => "OH. MY. GOD! Zelda YOU ARE MOST DEFINITELY LIKE THE BEST MAN SLASH WOMAN EVER I LOVE YOU AND WE SHOULD RUN AWAY SOMEWHERE"
```

### Parameters and Arity

Clojure functions can be defined with zero or more parameters. The values you pass to functions are called arguments, and the arguments can be of any type. The number of parameters is the function’s arity.

```clj
(defn no-params
  []
  "I take no parameters!")
(defn one-param
  [x]
  (str "I take one parameter: " x))
(defn two-params
  [x y]
  (str "Two parameters! That's nothing! Pah! I will smoosh them "
  "together to spite you! " x y))
```

In these examples, no-params is a **0-arity** function, one-param is **1-arity**, and two-params is **2-arity**. Functions also support arity overloading. This means that you can define a function so a different function body will run depending on the arity. Arity overloading is one way to provide default values for arguments.

```clj
(defn multi-arity
  ;; 3-arity arguments and body
  ([first-arg second-arg third-arg]
    (do-things first-arg second-arg third-arg))
  ;; 2-arity arguments and body
  ([first-arg second-arg]
    (do-things first-arg second-arg))
  ;; 1-arity arguments and body
  ([first-arg]
    (do-things first-arg)))

; In the following example "karate" is the default argument for the chop-type argument
(defn x-chop
  "Describe the kind of chop you're inflicting on someone"
  ([name chop-type]
    (str "I " chop-type " chop " name "! Take that!"))
  ([name]
    (x-chop name "karate")))
```

Clojure also allows you to define variable-arity functions by including a rest parameter, as in “put the rest of these arguments in a list with the following name.” The rest parameter is indicated by an ampersand **&**.

```clj
(defn codger-communication
  [whippersnapper]
  (str "Get off my lawn, " whippersnapper "!!!"))

(defn codger
  [& whippersnappers]
  (map codger-communication whippersnappers))

(codger "Billy" "Anne-Marie" "The Incredible Bulk")
; => ("Get off my lawn, Billy!!!"
;      "Get off my lawn, Anne-Marie!!!"
;      "Get off my lawn, The Incredible Bulk!!!")

; You can mix rest parameters with normal parameters, but the rest parameter has to come last
(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: "
      (clojure.string/join ", " things)))

(favorite-things "Doreen" "gum" "shoes" "kara-te")
; => "Hi, Doreen, here are my favorite things: gum, shoes, kara-te"
```

### Destructuring

The basic idea behind destructuring is that it lets you concisely bind names to values within a collection.

```clj
;; Return the first element of a collection
(defn my-first
  [[first-thing]] ; Notice that first-thing is within a vector
  first-thing)

(my-first ["oven" "bike" "war-axe"])
; => "oven"

(defn chooser
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring the rest of your choices. "
                "Here they are in case you need to cry over them: "
                (clojure.string/join ", " unimportant-choices))))

(chooser ["Marmalade", "Handsome Jack", "Pigpen", "Aquaman"])
; => Your first choice is: Marmalade
; => Your second choice is: Handsome Jack
; => We're ignoring the rest of your choices. Here they are in case \
;     you need to cry over them: Pigpen, Aquaman
```

You can also destructure maps.

```clj
(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(announce-treasure-location {:lat 28.22 :lng 81.33})
; => Treasure lat: 28.22
; => Treasure lng: 81.33

; We often want to just break keywords out of a map, so there’s a shorter syntax for that
; You can retain access to the original map argument by using the :as keyword
(defn receive-treasure-location
  [{:keys [lat lng] :as treasure-location}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng))

  ;; One would assume that this would put in new coordinates for your ship
  (steer-ship! treasure-location))
```

### Function body

The function body can contain forms of any kind. Clojure automatically returns the last form evaluated.

```clj
(defn number-comment
  [x]
  (if (> x 6)
    "Oh my gosh! What a big number!"
    "That number's OK, I guess"))

(number-comment 5)
; => "That number's OK, I guess"

(number-comment 7)
; => "Oh my gosh! What a big number!"
```

## Anonymous functions

In Clojure, functions don’t need to have names. You create anonymous functions in two ways. The first is to use the fn form.

```clj
(fn [param-list]
  function body)

(map (fn [name] (str "Hi, " name))
     ["Darth Vader" "Mr. Magoo"])
; => ("Hi, Darth Vader" "Hi, Mr. Magoo")

((fn [x] (* x 3)) 8)
; => 24
```

Clojure also offers another, more compact way to create anonymous functions.

```clj
(#(* % 3) 8)
; => 24

(map #(str "Hi, " %)
     ["Darth Vader" "Mr. Magoo"])
; => ("Hi, Darth Vader" "Hi, Mr. Magoo")

(#(str %1 " and " %2) "cornbread" "butter beans")
; => "cornbread and butter beans"

(#(identity %&) 1 "blarg" :yip)
; => (1 "blarg" :yip)
```

The percent sign, **%**, indicates the argument passed to the function. If your anonymous function takes multiple arguments, you can distinguish them like this: **%1**, **%2**, **%3**, and so on. **%** is equivalent to **%1**. You can also pass a rest parameter with **%&**.

## Returning functions

You’ve seen that functions can return other functions. The returned functions are closures, which means that they can access all the variables that were in scope when the function was created.

```clj
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(inc3 7)
; => 10
```