package io.github.electricmind.autocomplete

import _root_.io.github.electricmind.autocomplete.Vocabulary.Vocabulary

/*
* A mapping NGrams to Words
*/

object NGram2Words {
  type NGram2Words = Map[String, Set[String]]

  def apply(words: Vocabulary) =
    (for {
      word <- words.toList
      ngrams <- (1 to 4).map(word.sliding(_).toList)
      ngram <- ngrams
    } yield {
        ngram -> word
      }).groupBy(_._1).map({
      case (ngram, pairs) => (ngram, pairs.map(_._2).toSet)
    }).toMap
}
