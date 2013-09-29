package test

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import autocomplete.Autocomplete
import java.io.ByteArrayInputStream
import autocomplete.Vocabulary
import autocomplete.NGram2Probabilities
import autocomplete.NGram2Words
import autocomplete.NGrams

class testAutocomplete extends FlatSpec with Matchers {

    val ac = new Autocomplete(0.7, 2)

    val sample = """
        Only a few flies was flying around a corpse at the morning.
        """

    def samplestream = new ByteArrayInputStream(sample.getBytes);

    def vocabulary = Vocabulary(samplestream)
 
    def n2ps = NGram2Probabilities(NGram2Words(vocabulary))

    def n2ws = NGram2Words(vocabulary)

    def ngs = NGrams(n2ps.keySet)

    "An n2ps" should "contain a,was" in {
        n2ps("a") should be(0.032 plusOrMinus 0.001)
        n2ps("was") should be(0.0081 plusOrMinus 0.001)
    }

    "An Autocomplete" should "select words with 80% probability" in {
        ac.select(
            n2ps, ngs).take(6) should be(List("", "e", "n", "a", "s", "f"))
    }

    "An autocomplete" should "make a map from ngrams" in {
        ac.ngram2Words(List("a", "was"), n2ws) should be(
            List(("a", Set("around", "a", "was", "at")), ("was", Set("was"))))
    }

    "An autocomplete" should "make  ngrams" in {
        ac.ngrams(n2ps, n2ws, ngs) should be (List)
    }
    
}