package io.github.electricmind.autocomplete

import scala.collection.immutable.MapProxy
/*
* Map ngrams to their probabilitities
*/

class NGram2Probabilities(val self: Map[String, Double])
    extends MapProxy[String, Double]

object NGram2Probabilities {
    def apply(map: NGram2Words) = {
        val count = map.map(_._2.size).sum
        new NGram2Probabilities(
            map.map({
                case (ng, ws) => ng -> ws.size.toDouble / count
            }))
    }
}


