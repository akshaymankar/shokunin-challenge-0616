import org.specs2.mutable.Specification

class FeatureParser$Test extends Specification {
  "FeatureParser#parse" should {
    "return empty feature list for empty string" in {
      FeatureParser.parseItem("") mustEqual None
    }
    "return features and dependencies when they are are given" in {
      val a = Feature("A")
      val b = Feature("B")
      val c = Feature("C")

      FeatureParser.parseItem("(A,B,C)[A->B,B->C]") mustEqual Some(List(a, b, c), List(Dependency(a,b), Dependency(b,c)))
    }

    "return None when from doesn't exist in current group" in {
      FeatureParser.parseItem("(A,B,C)[D->A]") mustEqual None
    }
  }
}
