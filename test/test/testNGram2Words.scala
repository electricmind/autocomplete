package io.github.electricmind

import java.io.ByteArrayInputStream

import io.github.electricmind.autocomplete.{NGram2Words, Vocabulary}
import org.scalatest.{FlatSpec, Matchers}

class testNGram2Words extends FlatSpec with Matchers {

  val sample = """
        Only a few flies was flying around a corpse at the morning.
               """

  def ngrams = NGram2Words(vocabulary)

  def vocabulary = Vocabulary(samplestream)

  def samplestream = new ByteArrayInputStream(sample.getBytes);

  def take(set: Set[String], n: Int) = set.toList.sorted.take(n)

  "An NGrams" should "contain some items" in {
    ngrams("a").size shouldEqual 4
    take(ngrams("a"), 2) should contain allOf("a", "around")
    ngrams("li") should contain("flies")
  }
}