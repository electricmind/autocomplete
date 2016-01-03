package io.github.electricmind.autocomplete

/*
* A tree of possible ngrams
*/

object NGrams {

  type NGram = String

  def level(x: NGram): Iterator[(NGram, NGram)] = {
    if (x.length > 1) {
      x.sliding(x.length - 1).map(_ -> x) ++
        x.sliding(x.length - 1).map(level(_)).flatten
    } else {
      Iterator()
    }
  }

  def apply(ngrams: Set[String]): Map[String, Set[String]] =
    (for {
      ngram <- ngrams
      (ngram1, ngram2) <- level(ngram)
    } yield (ngram1, ngram2)).groupBy(_._1).map {
      case (x, y) => x -> y.map(_._2)
    }.toMap.withDefaultValue(Set())
}

