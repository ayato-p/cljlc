(defproject org.clojars.ayato_p/cljlc "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"Atilika Open Source repository" "http://www.atilika.org/nexus/content/repositories/atilika"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.atilika.kuromoji/kuromoji "0.7.7"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]}})
