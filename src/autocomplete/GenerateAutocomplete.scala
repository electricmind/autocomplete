package io.github.electricmind.autocomplete

import _root_.io.github.electricmind.autocomplete.Vocabulary.Word
import ru.wordmetrix.smartfile._

import scala.util.Try

/*
* A console program to generate autocomplete database
*/

object GenerateAutocomplete extends App {
  scala.util.Random
  case class CFG(size: Int = 1000,
                 probability: Double = 0.8d,
                 isAllowed: String => Boolean = (s:String) => s.forall(_.isLetterOrDigit),
                 normalize: String => String = identity,
                 path: String = "",
                 fs: Iterable[String] = Iterable.empty)

  def parse(args: List[String]): CFG = args match {
    case "-s" :: size :: args => parse(args).copy(size = size.toInt)

    case "-p" :: p :: args => parse(args).copy(probability = p.toDouble)

    case "-c" :: chars :: args =>
      val charSet = chars.toSet
      parse(args).copy(isAllowed = (s: String) => s.forall(charSet contains _))

    case "-l" :: args => parse(args).copy(normalize = s => s.toLowerCase)

    case f :: args => parse(args).copy(path = f, fs = args)

    case Nil => CFG()
  }

  override def main(args: Array[String]) = {

    parse(args.toList) match {
      case CFG(_, _, _, _, _, Nil) =>
        println(
          """
            |scala generate.GenerateAutocomplite [options] [path] [file []]
            |where:
            |  options -
            |    -s <Int> - a size of a bunch of words (1000);
            |
            |    -p <Double> - a probability of substring;
            |        
            |    -c <String> - allowed characters;
            |        
            |    -l - change all words to lower case
            |
            |  path - a path to output directory
            |
            |  file - text file
          """.stripMargin)
      case CFG(size, probability, isAllowed, normalize, path, fs) =>
        for {
          (ngram, ws: Set[Word]) <- Autocomplete(probability, size, isAllowed, normalize) {
            for {
              f <- fs.toIterator
              line <- Try {
                SmartFile.fromString(f).readLines()
              } recover {
                case th: Throwable =>
                  println(s"Can't read file $f: $th")
                  Iterator.empty
              } get
            } yield line
          }
        } {
          try {
            (SmartFile.fromString(path) / s"$ngram.txt").write(ws)
          } catch {
            case th: Throwable => println("Can't create file %s.txt: %s".format(ngram, th), th)
          }
        }
    }
  }
}