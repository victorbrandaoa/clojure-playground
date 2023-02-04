# Creating a project

If you don't have Leiningen, please install with the following command:

```sh
sudo apt install leiningen
```

Inside de directory **clojure_basic** use the following command:

```sh
lein new app clojure-basic
```

The command creates a project structure. Then you can, in **clojure-basic** directory, run your code using:

```sh
lein run

#or

lein repl
```

# Exercises

The code that answer the following questions is in the core.clj file in the project.

## Questions

1. Use the str, vector, list, hash-map, and hash-set functions.

2. Write a function that takes a number and adds 100 to it.

3. Write a function, dec-maker, that takes a number as parameter and returns a function that takes another number and return the subtraction between the both numbers.

4. Write a function, mapset, that works like map except the return value is a set

5. Create a function symmetrize-body-parts that works with weird space aliens with radial symmetry. Instead of two eyes, arms, legs, and so on, they have five. The function has to symmetrize the body parts.

6. Create a function that generalizes the function you created in Exercise 5. The new function should take a collection of body parts and the number of matching body parts to add.

## Execute the answer code

Inside the clojure-basic directory run the following command, after that you can call all the functions in core.clj to see them working.

```sh
lein repl
```