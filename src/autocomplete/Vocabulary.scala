package io.github.electricmind.autocomplete

import scala.collection.immutable.Set
import ru.wordmetrix.nlp.NLP._
import java.io.File
import java.io.InputStream
import scala.io.Source

class Vocabulary(set: Set[String]) extends Set[String] {
    def iterator: Iterator[String] = set.iterator

    def -(elem: String): scala.collection.immutable.Set[String] = set

    def +(elem: String): scala.collection.immutable.Set[String] =
        new Vocabulary(set + elem)

    def contains(elem: String): Boolean = set.contains(elem)

    override def toString() = "Vocabilary(" + set.toString + ")"
}

object Vocabulary {
    def apply() = new Vocabulary(Set[String]());

    type Phrase = String
    type Word = String

    def apply(phrases: Iterator[Phrase]) =
        new Vocabulary(phrases.map(_.tokenize()).flatten.toSet)

    def apply(fin: File): Vocabulary = {
        apply(Source.fromFile(fin).getLines())
    }

    def apply(sin: InputStream): Vocabulary = {
        apply(Source.fromInputStream(sin).getLines())
    }
}
