(ns cljlc.core
  (:import [org.atilika.kuromoji Token Tokenizer])
  (:require [clojure.string :as str]))

(def ^:private ignore-chars
  #"[\.\,\s\!\"\$\%\'\(\)\*\+\-\:\;\<\=\>\?\@\[\¥\]\^\_\`\{\|\}\~\#\&\/、。；：’”＜＞？・「」｛｝＋＝＿ー）（＊＆＾％＄＃＠！〜｀]")

(defn- filter-ignore-chars [s]
  (str/join
   (filter #((complement re-matches) ignore-chars (str %))
           s)))

(defn- only-alphabet? [s]
  (re-matches #"^[\w\.\,\s\!\"\$\%\'\(\)\*\+\-\:\;\<\=\>\?\@\[\¥\]\^\_\`\{\|\}\~\#\&\/]+$" s))

(defn- jp-src->reading [src]
  (let [tokenizer (.build (Tokenizer/builder))]
    (->> src
         (.tokenize tokenizer)
         (map #(let [features (.getAllFeaturesArray %)]
                 (if (> (count features) 7)
                   (nth features 7)
                   (.getSurfaceForm %))))
         str/join)))

(defn palindrome? [s]
  (let [converter (comp (if (only-alphabet? s) str/lower-case jp-src->reading)
                        filter-ignore-chars)
        converted-str (converter s)
        reversed-str (str/reverse converted-str)]
    (= converted-str reversed-str)))
