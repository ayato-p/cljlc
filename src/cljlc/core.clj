(ns cljlc.core
  (:import [org.atilika.kuromoji Token Tokenizer])
  (:require [clojure.string :as str]))

(def ^:private ignore-chars
  #"[\.\,\s\!\"\$\%\'\(\)\*\+\-\:\;\<\=\>\?\@\[\¥\]\^\_\`\{\|\}\~\#\&\/、。；：’”＜＞？・「」｛｝＋＝＿ー）（＊＆＾％＄＃＠！〜｀…☆]")

(def ^:private small-chars
  #"[ァィゥェォヵヶャュョッ]")

(def ^:private convert-map
  {"ァ" "ア"
   "ィ" "イ"
   "ゥ" "ウ"
   "ェ" "エ"
   "ォ" "オ"
   "ヵ" "カ"
   "ヶ" "ケ"
   "ャ" "ヤ"
   "ュ" "ユ"
   "ョ" "ヨ"
   "ッ" "ツ"})

(defn- filter-ignore-chars [s]
  (str/join
   (filter #((complement re-matches) ignore-chars (str %))
           s)))

(defn- only-alphabet? [s]
  (re-matches #"^[\w\.\,\s\!\"\$\%\'\(\)\*\+\-\:\;\<\=\>\?\@\[\¥\]\^\_\`\{\|\}\~\#\&\/]+$" s))

(defn- jp-small->large [s]
  (str/replace s small-chars #(get convert-map %)))

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
  (let [converter (comp filter-ignore-chars
                        (if (only-alphabet? s)
                          str/lower-case
                          #(-> % jp-src->reading jp-small->large)))
        converted-str (converter s)
        reversed-str (str/reverse converted-str)]
    (= converted-str reversed-str)))
