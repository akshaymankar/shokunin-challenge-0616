import scala.util.parsing.combinator._

object FeatureParser extends RegexParsers {
  private type Line = (Set[Feature], Set[Dependency])

  def feature = "[A-Z]".r ^^ { new Feature(_)}
  def dependency = ((feature <~ "->") ~ feature).map(x => Dependency(x._1, x._2))
  def featureGroup = ("(" ~> repsep(feature, ",") <~ ")").map(_.toSet)
  def dependencyGroup = ("[" ~> repsep(dependency, ",") <~ "]").map(_.toSet)
  def inputLine: Parser[Line] = (featureGroup ~ dependencyGroup).flatMap { x =>
    val features = x._1
    val dependencies = x._2
    if(dependencies.map(_.from).diff(features).isEmpty)
      success(features,dependencies)
    else
      failure("Rule Violated!")
  }

  def allInput: Parser[List[Line]] = rep(inputLine)

  def parseItem(s: String): Option[(Set[Feature], Set[Dependency])] = Option(parseAll(allInput, s).getOrElse(null)).map {groups =>
    val features = groups.map(_._1).fold(Set.empty)(_ ++ _)
    val dependencies = groups.map(_._2).fold(Set.empty)(_ ++ _)
    (features, dependencies)
  }
}
