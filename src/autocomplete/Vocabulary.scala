package io.github.electricmind.autocomplete

import scala.collection.immutable.Set
import ru.wordmetrix.nlp.NLP._
import java.io.File
import java.io.InputStream
import scala.io.Source

object Vocabulary {
    type Vocabulary = Set[String]
    type Phrase = String
    type Word = String

    def apply(phrases: Iterator[Phrase]) =
        phrases.map(_.tokenize()).flatten.toSet

    def apply(fin: File): Vocabulary = {
        apply(Source.fromFile(fin).getLines())
    }

    def apply(sin: InputStream): Vocabulary = {
        apply(Source.fromInputStream(sin).getLines())
    }
}
