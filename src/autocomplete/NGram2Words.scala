package autocomplete

import scala.collection.immutable.MapProxy
/*
* A mapping NGrams to Words
*/
class NGram2Words(val self: Map[String, Set[String]])
    extends MapProxy[String, Set[String]];

object NGram2Words {
    def apply(words: Vocabulary) =
        new NGram2Words(words.map(w => {
            (1 to 4).map(w.sliding(_)).flatten.map((_, w))
        }).flatten.groupBy(_._1).map({
            case (ngram, pairs) => (ngram, pairs.map(_._2))
        }).toMap)
}
