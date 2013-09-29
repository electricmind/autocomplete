package test

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
        Vocabulary(samplestream).toList.length should be(12)

        Vocabulary(samplestream).toList.sorted.take(3) should be(
            List("Only", "a", "at"))
    }
    
}

