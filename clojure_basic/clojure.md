# Syntax

## Forms

All clojure code is written in an uniform structure. Clojure recognizes two kind os structures:

* Literal representations of data structures;
* Operations.

We can call valid code as expressions or forms.

Clojure operations take the following form:

```clj
(operator operand1 operand2 ... operandn)
```

OBS: Notice that there are no commas. Clojure uses whitespace to separate operands, and it treats commas as whitespace.

```clj
(+ 1 2 3)
; => 6

(str "It was the panda " "in the library " "with a dust buster")
; => "It was the panda in the library with a dust buster"
```

## Flow Control

### If expression

Take a look in the if expression syntax.

```clj
(if boolean-form
  then-form
  optional-else-form)
```

```clj
(if true
  "By Zeus's hammer!"
  "By Aquaman's trident!")
; => "By Zeus's hammer!"

(if false
  "By Zeus's hammer!"
  "By Aquaman's trident!")
; => "By Aquaman's trident!"
```

OBS: You can also omit the else branch. If you do that and the Boolean expression is false, Clojure returns nil.

Notice that if uses operand position to associate operands with the then and else branches: the first operand is the then branch, and the second operand is the (optional) else branch. As a result, each branch can have only one form.

To get around this apparent limitation, you can use the do operator.

## Do operator

The do operator lets you wrap up multiple forms in parentheses and run each of them.

```clj
(if true
  (do (println "Success!")
      "By Zeus's hammer!")
  (do (println "Failure!")
      "By Aquaman's trident!"))
; => Success!
; => "By Zeus's hammer!"
```

## When operator

The when operator is like a combination of if and do, but with no else branch.

```clj
(when true
  (println "Success!")
  "abra cadabra")
; => Success!
; => "abra cadabra"
```

Use when if you want to do multiple things when some condition is true, and you always want to return nil when the condition is false.

## Other operators

Clojure has true and false values. nil is used to indicate no value in Clojure. You can check if a value is nil with the appropriately named **nil?** function.

```clj
(nil? 1)
; => false

(nil? nil)
; => true
```

Clojure’s equality operator is **=**.

```clj
(= 1 1)
; => true

(= nil nil)
; => true

(= 1 2)
; => false
```

Clojure uses the Boolean operators **or** and **and**. **or** returns either the first truthy value or the last value. **and** returns the first falsey value or, if no values are falsey, the last truthy value.

```clj
(or false nil :large_I_mean_venti :why_cant_I_just_say_large)
; => :large_I_mean_venti

(or (= 0 1) (= "yes" "no"))
; => false

(or nil)
; => nil

(and :free_wifi :hot_coffee)
; => :hot_coffee

(and :feelin_super_cool nil false)
; => nil
```

## Naming values with def

You use **def** to bind a name to a value in Clojure.

```clj
(def failed-protagonist-names
  ["Larry Potter" "Doreen the Explorer" "The Incredible Bulk"])
```

Notice the term **bind**, whereas in other languages you’d say you’re assigning a value to a variable. Those other languages typically encourage you to perform multiple assignments to the same variable.

However, changing the value associated with a name like this can make it harder to understand your program’s behavior because it’s more difficult to know which value is associated with a name or why that value might have changed.

```clj
; Don't do this

(def severity :mild)
(def error-message "OH GOD! IT'S A DISASTER! WE'RE ")
(if (= severity :mild)
  (def error-message (str error-message "MILDLY INCONVENIENCED!"))
  (def error-message (str error-message "DOOOOOOOMED!")))

; Do this

(defn error-message
  [severity]
  (str "OH GOD! IT'S A DISASTER! WE'RE "
       (if (= severity :mild)
         "MILDLY INCONVENIENCED!"
         "DOOOOOOOMED!")))

(error-message :mild)
; => "OH GOD! IT'S A DISASTER! WE'RE MILDLY INCONVENIENCED!"
```