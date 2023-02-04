# Exercises

The code that answer the following questions is in the core.clj file in the project.

## Questions

Context: The FWPD has a fancy new database technology called CSV (comma-separated values). Your job is to parse this state-of-the-art CSV and analyze it for potential vampires. All the functions you need are in the core.clj file.

1. Try implementing **map** using **reduce**, and then do the same for **filter** and **some**.

2. Turn the result of your glitter filter into a list of names.

3. Write a function, **append**, which will append a new suspect to your list of suspects.

4. Write a function, **validate**, which will check that **:name** and **:glitter-index** are present when you **append**. The **validate** function should accept two arguments: a map of keywords to validating functions, similar to **conversions**, and the record to be validated.

5. Write a function that will take your list of maps and convert it back to a CSV string. You’ll need to use the **clojure.string/join** function.