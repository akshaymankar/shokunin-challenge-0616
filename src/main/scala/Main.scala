import DependencyResolver.ResolutionSuccess

object Main {
  def main(args: Array[String]): Unit = {
    val input = scala.io.Source.stdin.getLines().mkString("\n")
    val resolvedList = FeatureParser.parseItem(input).getOrElse(throw new RuntimeException("Garbage in -> Garbage out"))
    val resolved = DependencyResolver.resolve(resolvedList._1, resolvedList._2)

    resolved match {
      case ResolutionSuccess(foo) => foo foreach { x =>
        println(x.map(_.toString).mkString(","))
      }
      case _ => println("Couldn't resolve the graph!")
    }
  }
}
