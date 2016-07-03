
trait ImplicitHelpers {
  implicit class FeatureString(s: String) {
    def toF: Feature = Feature(s)
    def toFs: Set[Feature] = s.split(",").map(_.toF).toSet
  }
}

