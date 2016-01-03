package io.github.electricmind

import java.io.ByteArrayInputStream

import io.github.electricmind.autocomplete._
import org.scalatest.{FlatSpec, Matchers}

class testVocabulary extends FlatSpec with Matchers {

  val sample = """
        Only a few flies was flying around a corpse at the morning.
               """

  def samplestream = new ByteArrayInputStream(sample.getBytes);

  "A vocabulary" should "be created" in {
    Vocabulary(samplestream) should have size 12

    Vocabulary(samplestream) should contain allOf("Only", "a", "at")
  }

}

