package io.github.electricmind

import java.io.ByteArrayInputStream

import io.github.electricmind.autocomplete.{NGram2Probabilities, NGram2Words, Vocabulary}
import org.scalatest.{FlatSpec, Matchers}

class testNGram2Probabilities extends FlatSpec with Matchers {

  val sample = """
        Only a few flies was flying around a corpse at the morning.
               """

  def ngrams = NGram2Probabilities(NGram2Words(vocabulary))

  def vocabulary = Vocabulary(samplestream)

  def samplestream = new ByteArrayInputStream(sample.getBytes);

  def take(set: Set[String], n: Int) = set.toList.sorted.take(n)

  "An NGrams" should "contain some items" in {
    ngrams("a") shouldEqual 0.032 +- 0.01
    ngrams("li") shouldEqual 0.0081 +- 0.0001
  }
}