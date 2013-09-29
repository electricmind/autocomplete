package autocomplete

import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.File
import java.io.FileOutputStream

object GenerateAutocomplete extends App {
    override def main(args: Array[String]) = {
        for {
            (ngram, words) <- new Autocomplete(0.8, 1000)(
                args.toIterator.map(x => { io.Source.fromFile(x).getLines }).flatten)
        } { //new File("%s.txt".format(ngram))
            println(ngram)
            try {
                val sout = new OutputStreamWriter(new FileOutputStream("%s.txt".format(ngram)))
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