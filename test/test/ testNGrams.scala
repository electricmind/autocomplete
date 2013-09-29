package test

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import java.io.ByteArrayInputStream
import autocomplete.Vocabulary
import autocomplete.NGram2Words
import autocomplete.NGrams

class testNGrams extends FlatSpec with Matchers {


    def ngrams = NGrams(Set("a","ab","abc","b","bc","c"))
    
    "An NGrams" should "contain some items" in {
        ngrams("a").keySet should be(Set("ab","abc"))
        ngrams("ab").keySet should be(Set("abc"))
        ngrams("abc").keySet should be(Set());
    }
    
    "An NGrams" should "add another ngrams" in {
        (ngrams + ("a" -> NGrams(Set("aq"))))("a").keySet should be(Set("ab","abc","aq"))
    }

    "An NGrams" should "exclude keys" in {
        (ngrams - "a").keySet should be(Set("a", "b","bc","c"))
    }
}