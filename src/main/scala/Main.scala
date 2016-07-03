object Main {
  def main(args: Array[String]): Unit = {
    val input = scala.io.StdIn.readLine()
    val resolvedList = FeatureParser.parseItem(input).getOrElse(throw new RuntimeException("Garbage in -> Garbage out"))
    val resolved = DependencyResolver.resolve(resolvedList._1, resolvedList._2)

    resolved foreach { x =>
      println(x.map(_.toString).mkString(","))
    }
  }
}
