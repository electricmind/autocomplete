package io.github.electricmind

import io.github.electricmind.autocomplete.NGrams
import org.scalatest.{FlatSpec, Matchers}

class testNGrams extends FlatSpec with Matchers {
  def ngrams = NGrams(Set("abc", "wow", "afk", "abc"))

  "An NGrams" should "contain some items" in {
    ngrams("a") should contain only("ab", "af")

    ngrams("ab") should contain only ("abc")

    ngrams("abc") should be('empty)

    ngrams("w") should contain only("wo", "ow")
  }
} 