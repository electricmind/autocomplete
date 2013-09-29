package test

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import java.io.ByteArrayInputStream
import autocomplete.Vocabulary
import autocomplete.NGram2Words

class testNGram2Words extends FlatSpec with Matchers {

    val sample = """
        Only a few flies was flying around a corpse at the morning.
        """

    def samplestream = new ByteArrayInputStream(sample.getBytes);
    
    def vocabulary = Vocabulary(samplestream)
    
    def ngrams = NGram2Words(vocabulary)
    
    def take(set : Set[String],n : Int) = set.toList.sorted.take(n)
    
    "An NGrams" should "contain some items" in {
        ngrams("a").size should be(4)
        take(ngrams("a"),2) should be(List("a","around"))
        ngrams("li") should be(Set("flies"))
     }
}