package autocomplete

import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.File
import java.io.FileOutputStream

object GenerateAutocomplete extends App {
    override def main(args: Array[String]) = {
        def parse(args: List[String]): (Double, Int, String, List[String]) = args match {
            case "-s" :: size :: args => parse(args) match {
                case (p, _, path, fs) => (p, size.toInt, path, fs)
            }

            case "-p" :: p :: args => parse(args) match {
                case (_, size, path, fs) => (p.toDouble, size, path, fs)
            }

            case f :: args => parse(args) match {
                case (p, size, ".", fp :: List()) => (p, size, f, fp :: List())
                case (p, size, path, fp :: fs) => (p, size, f, path :: fp :: fs)
                case (p, size, path, List())   => (p, size, path, f :: List())
            }

            case List() =>
                (0.8d, 1000, ".", List())
        }

        val (probability, size, path, fs) = parse(args.toList)
        if (fs.length == 0) {
            println(
                """
     scala generate.GenerateAutocomplite [options] [path] [file []]
         where options are :
             -s <Int> - a size of a bunch of words (1000);
                    
             -p <Double> - a probability of substring.
                    
              path - a path to output directory
                    
              file - text file
 """)
        } else {
            for {
                (ngram, words) <- new Autocomplete(probability, size)(
                    fs.toIterator.map(x => { println(x); io.Source.fromFile(x).getLines }).flatten)
            } {
                try {
                    val sout = new OutputStreamWriter(new FileOutputStream(new File(path,"%s.txt".format(ngram))))
                    for (word <- words) {
                        sout.write(word + "\n")
                    }
                    sout.close()
                } catch {
                    case x => println("Can't create file %s.txt: %s".format(ngram, x))
                }
            }
        }
    }
}