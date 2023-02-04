(ns fwpd.core)

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
        (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
          (reduce (fn [row-map [vamp-key value]]
                    (assoc row-map vamp-key (convert vamp-key value)))
                  {}
                  (map vector vamp-keys unmapped-row)))
        rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

;; Question 1
(defn map-with-reduce [f data]
  (reduce #(conj %1 (f %2)) [] data))

(defn filtra [f data res]
  (if (f data)
    (conj res data)
    res))

(defn filter-with-reduce [f data]
  (reduce #(filtra f %2 %1) [] data))

(defn some-with-reduce [f data]
  (not (empty? (filter-with-reduce f data))))

;; Question 2
(println (into '() (glitter-filter 3 (mapify (parse (slurp filename))))))

;; Question 3
(def suspects (mapify (parse (slurp filename))))

(defn append [suspects-list new-suspect]
  (conj suspects-list new-suspect))

;; Question 4
(def validations {:name contains? 
                  :glitter-index contains?})

(def new-vamp {:name "Damon Salvatore" :glitter-index 0})

(defn validate [functions vamp]
  (and ((get functions :name) vamp :name) ((get functions :glitter-index) vamp :glitter-index)))

(defn validated-append [suspects-list new-suspect]
  (if (validate validations new-suspect)
    (conj suspects-list new-suspect)))

;; Question 5
(defn map-list-string [suspect-list]
  (clojure.string/join "\n" (map (fn [suspect-data]
    (clojure.string/join "," suspect-data)) (map (fn [suspect]
    (list (get suspect :name) (get suspect :glitter-index))), suspect-list))))
