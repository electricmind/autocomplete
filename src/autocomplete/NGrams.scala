package autocomplete
object NGrams {

    type NGram = String

    def level(x: NGram): Iterator[(NGram, NGram)] = {
        if (x.length > 1) {
            x.sliding(x.length-1).map(_ -> x) ++
                x.sliding(x.length-1).map(level(_)).flatten
        } else {
            Iterator()
        }
    }
    def apply(ngrams: Set[String]): Map[String, Set[String]] = {
        ngrams.map(level(_)).flatten.groupBy(_._1).
            map({ case (x, y) => x -> y.map(_._2) }).
            toMap.withDefaultValue(Set())

    }
}


//TODO: remove old code 
//class NGrams(Map) extends Map[String,Set[String]] {
//    //TODO: eliminate possible glitch with B1 >: Ngrams and asIntanseOf[NGrams]
//    def + [B1 >: autocomplete.NGrams](kv: (String, B1)): Map[String,NGrams] = {
//        new NGrams(set | kv._2.asInstanceOf[NGrams].set)
//    } 
//    // Members declared in  scala.collection.MapLike 
//    def -(key: String): Map[String,autocomplete.NGrams] = 
//        new NGrams(set.filterNot(_ contains key) + key)
//    
//    def get(key: String):  Option[autocomplete.NGrams] = 
//        Some(new NGrams(set.filter(_ contains key).filterNot(_ == key)))
//    
//    def iterator: Iterator[(String, autocomplete.NGrams)] = 
//        set.toIterator.map(x => x -> get(x).get)
//        
//}