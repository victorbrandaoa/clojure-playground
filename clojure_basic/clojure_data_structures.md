# Data Structures

All of Clojure’s data structures are immutable, meaning you can’t change them in place.

## Numbers

We’ll work with integers and floats. We’ll also work with ratios, which Clojure can represent directly. Here’s an integer, a float, and a ratio, respectively.

```clj
93
1.2
1/5
```

## Strings

Strings represent text. Here are some examples of string literals.

```clj
"Lord Voldemort"
"\"He who must not be named\""
"\"Great cow of Moscow!\" - Hermes Conrad"
```

OBS: Notice that Clojure only allows double quotes to delineate strings.

## Maps

Maps are similar to dictionaries or hashes in other languages. They’re a way of associating some value with some other value. The two kinds of maps in Clojure are hash maps and sorted maps.

```clj
; An empty map
{}

{:first-name "Charlie"
 :last-name "McFishwich"}

; Here we associate "string-key" with the + function:
{"string-key" +}

; Maps can be nested
{:name {:first "John" :middle "Jacob" :last "Jingleheimerschmidt"}}
```

```clj
; ----- Creating a map -----

(hash-map :a 1 :b 2)
; => {:a 1 :b 2}

; ----- Look up values in maps -----

(get {:a 0 :b 1} :b)
; => 1

(get {:a 0 :b {:c "ho hum"}} :b)
; => {:c "ho hum"}

(get {:a 0 :b 1} :c)
; => nil

(get {:a 0 :b 1} :c "unicorns?")
; => "unicorns?"

; ----- The get-in function lets you look up values in nested maps -----

(get-in {:a 0 :b {:c "ho hum"}} [:b :c])
; => "ho hum"

; ----- Another way to look up a value in a map is to treat the map like a function with the key as its argument -----

({:name "The Human Coffeepot"} :name)
; => "The Human Coffeepot"
```

## Keywords

Clojure keywords are best understood by seeing how they’re used. They’re primarily used as keys in maps, as you saw in the preceding section.

```clj
:a
:rumplestiltsken
:34
:_?
```

Keywords can be used as functions that look up the corresponding value in a data structure.

```clj
(:a {:a 1 :b 2 :c 3})
; => 1

(:d {:a 1 :b 2 :c 3} "No gnome knows homes like Noah knows")
; => "No gnome knows homes like Noah knows"
```

## Vectors

A vector is similar to an array, in that it’s a 0-indexed collection.

```clj
[3 2 1]

(get [3 2 1] 0)
; => 3

(get ["a" {:name "Pugsley Winterbottom"} "c"] 1)
; => {:name "Pugsley Winterbottom"}

; Creating a vector
(vector "creepy" "full" "moon")
; => ["creepy" "full" "moon"]

; Adding an element at the end of the vector
(conj [1 2 3] 4)
; => [1 2 3 4]
```

## Lists

Lists are similar to vectors in that they’re linear collections of values. But there are some differences. For example, you can’t retrieve list elements with **get**.

```clj
'(1 2 3 4)
; => (1 2 3 4)

; If you want to retrieve an element from a list, you can use the nth function
(nth '(:a :b :c) 0)
; => :a

(nth '(:a :b :c) 2)
; => :c

; Creating a list
(list 1 "two" {3 4})
; => (1 "two" {3 4})

; Adding an element at the beginig of the list
(conj '(1 2 3) 4)
; => (4 1 2 3)
```

If you need to easily add items to the beginning of a sequence or if you’re writing a macro, you should use a list. Otherwise, you should use a vector.

## Sets

Sets are collections of unique values. Clojure has two kinds of sets: hash sets and sorted sets.

```clj
#{"kurt vonnegut" 20 :icicle}

; Creating a set
(hash-set 1 1 2 2)
; => #{1 2}

; Adding elements into the set
(conj #{:a :b} :b)
; => #{:a :b}

; Creating a set from other data structures
(set [3 3 3 4 4])
; => #{3 4}
```

```clj
; Using contains
(contains? #{:a :b} :a)
; => true

(contains? #{:a :b} 3)
; => false

(contains? #{nil} nil)
; => true

; Using Keywords
(:a #{:a :b})
; => :a

; Using get
(get #{:a :b} :a)
; => :a

(get #{:a nil} nil)
; => nil

(get #{:a :b} "kurt vonnegut")
; => nil
```

Notice that using **get** to test whether a set contains **nil** will always return **nil**, which is confusing.