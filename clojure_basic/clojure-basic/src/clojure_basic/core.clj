(ns clojure-basic.core
  (:gen-class))

;; Question 1
(defn -main
  "I do a fill things."
  [& args]
  (println (str "Hello, " "Victor"))
  (println (vector 1 2 3 4 5))
  (println (list 1 2 3 4 5))
  (println (hash-map :a 1 :b 2 :c 3))
  (println (hash-set 1 1 1 3 3 4 5 2 2)))

;; Question 2
(defn add-100
  [number]
  (+ number 100))

;; Question 3
(defn dec-maker
  [number]
  #(- % number))

;; Question 4
(defn mapset
  [sequence]
  (let [maped-sequence (map inc sequence)]
    (set maped-sequence)))

;; Questions 5 and 6
(def asym-alien-body-parts [{:name "head" :size 3}
                            {:name "1-eye" :size 1}
                            {:name "1-ear" :size 1}
                            {:name "mouth" :size 1}
                            {:name "nose" :size 1}
                            {:name "neck" :size 2}
                            {:name "1-shoulder" :size 3}
                            {:name "1-upper-arm" :size 3}
                            {:name "chest" :size 10}
                            {:name "back" :size 10}
                            {:name "1-forearm" :size 3}
                            {:name "abdomen" :size 6}
                            {:name "1-kidney" :size 1}
                            {:name "1-hand" :size 2}
                            {:name "1-knee" :size 2}
                            {:name "1-thigh" :size 4}
                            {:name "1-lower-leg" :size 3}
                            {:name "1-achilles" :size 1}
                            {:name "1-foot" :size 2}])

(defn match-part
  [part index]
  {:name (clojure.string/replace (:name part) #"^\d-" (str index "-"))
  :size (:size part)})

;; To generalize we can receive the number of matching parts as parameter
(defn symmetrize-body-parts
  [asym-body-parts]
  (reduce (fn [total-body-parts part]
    (into total-body-parts (set 
      (loop [part part iter 1 result-body-parts [part]]
        (if (> iter 5)
          result-body-parts
          (recur part (inc iter) (conj result-body-parts (match-part part iter))))))))
          [] asym-body-parts))

(defn show-res
  [res]
  (let [final-index (- (count res) 1)]
    (loop [iter 0]
      (println (get res iter))
      (if (= iter final-index)
      (println "End!!")
      (recur (inc iter))))))
