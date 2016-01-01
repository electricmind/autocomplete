package io.github.electricmind.autocomplete

import scala.collection.mutable.PriorityQueue
/*
* An Autocomplete class that generates an autocomplete dataset.
*/
class Autocomplete(probability: Double = 0.8, size: Int = 1000) {
    type NGram = String
    type Word = String
    type NGrams = Map[NGram, Set[NGram]]

    def select(n2ps: NGram2Probabilities, ngs: NGrams): Iterable[NGram] =
        (n2ps.keys.toList.map(x => (x, n2ps(x))).sortBy(-_._2).
            scanLeft((0d, "asdasda"))({
                case ((p, _), (x, px)) => (p + px, x)
            }).
            takeWhile({ case (p, x) => p < probability }).
            map(_._2)).tail

    def ngrams(n2ps: NGram2Probabilities, n2ws: NGram2Words, ngs: NGrams) = {
        val queue: PriorityQueue[NGram] = new PriorityQueue[NGram]()(
            new Ordering[NGram] {
                def compare(x: NGram, y: NGram) =
                    n2ws(x).size compare n2ws(y).size
            }) ++ "abcdefgihjklmnopqrstuvwxyz".split("").tail.filter(ngs contains _).toSet

        def ngrams(outcome: List[NGram] = List(),
                   p: Double = 0.0): List[NGram] =
            if (p < probability) {
                queue.headOption match {
                    case Some(ngram) if n2ws(ngram).size > size => {
                        queue.dequeue
                        ngs(ngram).map(x => queue.enqueue(x))
                        ngrams(outcome, p)
                    }
                    case Some(ngram) => {
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
        apply(n2ps, n2ws, ngs)
    }
}
