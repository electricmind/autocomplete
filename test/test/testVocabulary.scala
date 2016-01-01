package io.github.electricmind

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import autocomplete._
import java.io.ByteArrayInputStream

class testVocabulary extends FlatSpec with Matchers {

    val sample = """
        Only a few flies was flying around a corpse at the morning.
        """

    def samplestream = new ByteArrayInputStream(sample.getBytes);

    "A vocabulary" should "be created" in {
        Vocabulary(samplestream) should have size 12

        Vocabulary(samplestream) should contain allOf ("Only", "a", "at")
    }
    
}

