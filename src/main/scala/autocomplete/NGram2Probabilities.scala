package io.github.electricmind.autocomplete

import _root_.io.github.electricmind.autocomplete.NGram2Words.NGram2Words

/*
* Map ngrams to their probabilitities
*/
object NGram2Probabilities {
  type NGram2Probabilities = Map[String, Double]

  def apply(map: NGram2Words) = {
    val count = map.map(_._2.size).sum
    map map {
      case (ng, ws) => ng -> ws.size.toDouble / count
    }
  }
}


