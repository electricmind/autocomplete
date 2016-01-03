package io.github.electricmind

import java.io.ByteArrayInputStream

import io.github.electricmind.autocomplete.{Autocomplete, NGram2Probabilities, NGram2Words, NGrams, Vocabulary}
import org.scalatest.{FlatSpec, Matchers}

class testAutocomplete extends FlatSpec with Matchers {

  val ac = new Autocomplete(0.8, 2, _ => true, identity)

  val sample = """
        Only a few flies was flying around a corpse at the morning.
               """

  def n2ws = NGram2Words(vocabulary)

  def vocabulary = Vocabulary(samplestream)

  def samplestream = new ByteArrayInputStream(sample.getBytes);

  def ngs = NGrams(n2ps.keySet)

  def n2ps = NGram2Probabilities(NGram2Words(vocabulary))

  "An n2ps" should "contain a,was" in {
    n2ps("a") shouldEqual 0.032 +- 0.001
    n2ps("was") shouldEqual 0.0081 +- 0.001
  }

  "An autocomplete" should "select words with 80% probability" in {
    println(ac.select(
      n2ps, ngs).toList)
    ac.select(n2ps, ngs) should contain allOf("e", "n", "a", "s", "f")
  }

  it should "make a map from ngrams" in {
    ac.ngram2Words(List("a", "was"), n2ws) should contain only(
      ("a", Set("around", "a", "was", "at")),
      ("was", Set("was")))
  }

  it should "make  ngrams" in {
    ac.ngrams(n2ps, n2ws, ngs) should contain allOf("ew", "mo")
  }

}