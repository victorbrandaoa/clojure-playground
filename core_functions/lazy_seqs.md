# Lazy Seqs

A lazy seq is a seq whose members aren’t computed until you try to access them. Deferring the computation until the moment it’s needed makes your programs more efficient, and it has the surprising benefit of allowing you to construct infinite sequences. Computing a seq’s members is called realizing the seq.

## Lazy Seq Efficiency

Imagine that you have a list with one million data and the function that you have take a second to process every data. Now imagine that you're going to use the function as parameter for **map**, the **map** function returns a Lazy Seq, but a nonlazy implementation of **map** would first have to apply the function to every data in the list before passing the result to another function for example.

Because you have one million data, this would take one million seconds or 12 days. Because **map** is lazy, it doesn’t actually apply the function to the list until you try to access the mapped element.

You can think of a lazy seq as consisting of two parts: a recipe for how to realize the elements of a sequence and the elements that have been realized so far. When you use **map**, the lazy seq it returns doesn’t include any realized elements yet, but it does have the recipe for generating its elements. Every time you try to access an unrealized element, the lazy seq will use its recipe to generate the requested element.

Considering our example with a million data and a function that takes a second to process each data. After the map returns the lazy seq, the operation of retrieve the first element took about 32 seconds. That’s much better than one million seconds, but it’s still 31 seconds more than we would have expected. After all, you’re only trying to access the very first element, so it should have taken only one second.

The reason it took 32 seconds is that Clojure chunks its lazy sequences, which just means that whenever Clojure has to realize an element, it preemptively realizes some of the next elements as well. Clojure does this because it almost always results in better performance. Thankfully, lazy seq elements need to be realized only once. Accessing the first element again takes almost no time.

## Infinite Seqs

One cool, useful capability that lazy seqs give you is the ability to construct infinite sequences. So far, you’ve only worked with lazy sequences generated from vectors or lists that terminated. However, Clojure comes with a few functions to create infinite sequences. One easy way to create an infinite sequence is with **repeat**, which creates a sequence whose every member is the argument you pass.

```clj
(concat (take 8 (repeat "na")) ["Batman!"])
; => ("na" "na" "na" "na" "na" "na" "na" "na" "Batman!")
```

You can also use repeatedly, which will call the provided function to generate each element in the sequence.

```clj
(take 3 (repeatedly (fn [] (rand-int 10))))
; => (1 4 0)
```

A lazy seq’s recipe doesn’t have to specify an endpoint. Functions like **first** and **take**, which realize the lazy seq, have no way of knowing what will come next in a seq, and if the seq keeps providing elements, well, they’ll just keep taking them.

```clj
(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(take 10 (even-numbers))
; => (0 2 4 6 8 10 12 14 16 18)
```
