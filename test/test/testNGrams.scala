package io.github.electricmind

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import java.io.ByteArrayInputStream
import autocomplete.Vocabulary
import autocomplete.NGram2Words
import autocomplete.NGrams

class testNGrams extends FlatSpec with Matchers {
    def ngrams = NGrams(Set("abc","wow","afk","abc"))
    println(ngrams)
    "An NGrams" should "contain some items" in {
        ngrams("a") should be(Set("ab","af"))
        ngrams("ab") should be(Set("abc"))
        ngrams("abc") should be(Set());
        ngrams("w") should be(Set("wo","ow"));
    }
} 