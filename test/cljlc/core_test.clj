(ns cljlc.core-test
  (:require [cljlc.core :refer :all]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [midje.sweet :refer :all]))

(def palindrome-source-en
  (with-open [r (io/reader "dev-resources/palindrome-en.txt")]
    (doall (line-seq r))))

(def palindrome-source-jp
  (with-open [r (io/reader "dev-resources/palindrome-jp.txt")]
    (doall (line-seq r))))

(facts "`palindrome?`"
  (doseq [s palindrome-source-en]
    (fact "for alphabet characters"
      (palindrome? s) => true))
  (doseq [s palindrome-source-jp]
    (fact "for japanese characters"
      (palindrome? s) => true)))
