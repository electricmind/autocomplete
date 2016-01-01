package io.github.electricmind

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import java.io.ByteArrayInputStream
import autocomplete.Vocabulary
import autocomplete.NGram2Words
import autocomplete.NGram2Probabilities

class testNGram2Probabilities extends FlatSpec with Matchers {

    val sample = """
        Only a few flies was flying around a corpse at the morning.
        """

    def samplestream = new ByteArrayInputStream(sample.getBytes);
    
    def vocabulary = Vocabulary(samplestream)
    
    def ngrams = NGram2Probabilities(NGram2Words(vocabulary))
    
    def take(set : Set[String],n : Int) = set.toList.sorted.take(n)
    
    "An NGrams" should "contain some items" in {
        ngrams("a") shouldEqual 0.032 +- 0.01
        ngrams("li") shouldEqual 0.0081 +- 0.0001
     }
}