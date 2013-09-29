package autocomplete
object NGrams {
  def apply(set: Set[String]) = {
    new NGrams(set)
  }
}


class NGrams(val set : Set[String]) extends Map[String,NGrams] {
    //TODO: eliminate possible glitch with B1 >: Ngrams and asIntanseOf[NGrams]
    def + [B1 >: autocomplete.NGrams](kv: (String, B1)): Map[String,NGrams] = {
        new NGrams(set | kv._2.asInstanceOf[NGrams].set)
    } 
    // Members declared in  scala.collection.MapLike 
    def -(key: String): Map[String,autocomplete.NGrams] = 
        new NGrams(set.filterNot(_ contains key) + key)
    
    def get(key: String):  Option[autocomplete.NGrams] = 
        Some(new NGrams(set.filter(_ contains key).filterNot(_ == key)))
    
    def iterator: Iterator[(String, autocomplete.NGrams)] = 
        set.toIterator.map(x => x -> get(x).get)
        
}