package io.github.electricmind.autocomplete

import io.github.electricmind.autocomplete.NGram2Probabilities.NGram2Probabilities
import io.github.electricmind.autocomplete.NGram2Words.NGram2Words

import scala.annotation.tailrec
import scala.collection.mutable.PriorityQueue

/*
* An Autocomplete class that generates an autocomplete dataset.
*/
object Autocomplete {
  type NGram = String
  type Word = String
  type NGrams = Map[NGram, Set[NGram]]

  def apply(probability: Double = 0.8,
            size: Int = 1000,
            isAllowed: String => Boolean = _ => true,
            normalize: String => String = identity): Iterator[String] => Map[NGram, Set[Word]] =
    new Autocomplete(probability, size, isAllowed, normalize) apply _
}

class Autocomplete(probability: Double,
                   size: Int,
                   isAllowed: String => Boolean,
                   normalize: String => String) {

  import Autocomplete._

  def select(n2ps: NGram2Probabilities, ngs: NGrams): Iterable[NGram] =
    n2ps.keys.toList.map(x => (x, n2ps(x)))
      .sortBy(-_._2)
      .scanLeft((0d, "")){
      case ((p, _), (x, px)) => (p + px, x)
    }.takeWhile { case (p, x) => p < probability }.
      map(_._2).tail

  def apply(ss: Iterator[String]): Map[NGram, Set[Word]] = {
    val vocabulary = Vocabulary(ss).filter(isAllowed).map(normalize)
    val n2ws = NGram2Words(vocabulary)
    val n2ps = NGram2Probabilities(n2ws)
    val ngs = NGrams(n2ps.keySet)
    apply(n2ps, n2ws, ngs)
  }

  def apply(n2ps: NGram2Probabilities, n2ws: NGram2Words, ngs: NGrams) =
    ngram2Words(ngrams(n2ps, n2ws, ngs), n2ws).toMap


  def ngrams(n2ps: NGram2Probabilities, n2ws: NGram2Words, ngs: NGrams) = {
    implicit val ordering = Ordering.fromLessThan((x: NGram, y: NGram) => n2ws(x).size < n2ws(y).size)

    val queue: PriorityQueue[NGram] = new PriorityQueue[NGram]() ++ ngs.keys.filter(_.length == 1)

    @tailrec
    def ngrams(outcome: List[NGram] = List(), p: Double = 0.0): List[NGram] =
      if (p < probability) {
        queue.headOption match {
          case Some(ngram) if n2ws(ngram).size > size =>
            queue.dequeue()
            ngs(ngram) foreach {x =>
              queue.enqueue(x)
            }
            ngrams(outcome, p)

          case Some(ngram) =>
            queue.dequeue()
            ngrams(ngram :: outcome, p + n2ps(ngram))

          case None => outcome
        }
      } else {
        outcome
      }
    ngrams()
  }

  def ngram2Words(ngrams: List[NGram], n2ws: NGram2Words) = ngrams map { x => x -> n2ws(x) }
}
