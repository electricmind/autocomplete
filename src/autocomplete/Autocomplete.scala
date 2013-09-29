package autocomplete;

import scala.collection.mutable.PriorityQueue

class Autocomplete(probability: Double = 0.8, size: Int = 1000) {
    type NGram = String;
    type Word = String;

    def select(n2ps: NGram2Probabilities, ngs: NGrams): Iterable[NGram] =
        (n2ps.keys.toList.map(x => { println(x); (x, n2ps(x)) }).sortBy(-_._2).
            scanLeft((0d, "asdasda"))({
                case ((p, _), (x, px)) => (p + px, x)
            }).
            takeWhile({ case (p, x) => p < probability }).
            map(_._2)).tail

    //TODO: function scanLeft

    def ngrams(n2ps: NGram2Probabilities, n2ws: NGram2Words, ngs: NGrams) = {
        val queue: PriorityQueue[NGram] = new PriorityQueue[NGram]()(
            new Ordering[NGram] {
                def compare(x: NGram, y: NGram) =
                    n2ws(x).size compare n2ws(y).size
            }) ++ "abcdefgihjklmnopqrstuvwxyz".split("").tail.toSet
        //            select(n2ps, ngs)

        def ngrams(outcome: List[NGram] = List(),
                   p: Double = 0.0): List[NGram] =
            if (p < probability) {
                queue.headOption match {
                    case Some(ngram) if n2ws(ngram).size > size => {
                        queue.dequeue
                        println(ngram, ngs(ngram).filter(_._1.size == ngram.size + 1).map(_._1))
                        println("1", p, n2ps(ngram))
                        //                        queue ++ ngs(ngram).keys
                        ngs(ngram).
                            keys.
                            filter(_.length == ngram.size + 1).
                            map(x => queue.enqueue(x))
                        ngrams(outcome, p)
                    }
                    case Some(ngram) => {
                        println("2", ngram, p, n2ps(ngram))
                        queue.dequeue; ngrams(ngram :: outcome, p + n2ps(ngram))
                    }
                    case None => outcome
                }
            } else {
                outcome
            }
        ngrams()
    }

    def ngram2Words(ngrams: List[NGram], n2ws: NGram2Words) =
        ngrams.map(x => x -> n2ws(x))

    def apply(n2ps: NGram2Probabilities, n2ws: NGram2Words, ngs: NGrams) = {
        ngram2Words(ngrams(n2ps, n2ws, ngs), n2ws).toMap
    }

    def apply(ss: Iterator[String]): Map[NGram, Set[Word]] = {
        val vocabulary = Vocabulary(ss)
        val n2ws = NGram2Words(vocabulary)
        val n2ps = NGram2Probabilities(n2ws)
        val ngs = NGrams(n2ps.keySet)
        //        println(n2ps)
        apply(n2ps, n2ws, ngs)
        //        n2ws
    }
}
