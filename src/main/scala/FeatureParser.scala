import scala.util.parsing.combinator._



object FeatureParser extends RegexParsers {
  def feature = "[A-Z]".r ^^ { new Feature(_)}
  def dependency = ((feature <~ "->") ~ feature).map(x => Dependency(x._1, x._2))
  def featureGroup = "(" ~> repsep(feature, ",") <~ ")"
  def dependencyGroup = "[" ~> repsep(dependency, ",") <~ "]"
  def inputLine: Parser[(List[Feature], List[Dependency])] = (featureGroup ~ dependencyGroup).map(x => (x._1, x._2))

  def parseItem(s: String): Option[(List[Feature], List[Dependency])] = parseAll(inputLine, s) match {
    case Success(line, _) =>
      val features = line._1
      val dependencies = line._2

      if(dependencies.map(_.from).diff(features).isEmpty)
        Option(line)
      else
        None
    case _ => None
  }
}
