import org.specs2.mutable.Specification

class FeatureParser$Test extends Specification with ImplicitHelpers {
  "FeatureParser#parse" should {
    "return features and dependencies when they are are given" in {
      val a = Feature("A")
      val b = Feature("B")
      val c = Feature("C")

      FeatureParser.parseItem("(A,B,C)[A->B,B->C]") mustEqual Some(Set(a, b, c), Set(Dependency(a,b), Dependency(b,c)))
    }

    "return None when from doesn't exist in current group" in {
      FeatureParser.parseItem("(A,B,C)[D->A]") mustEqual None
    }

    "return features and dependencies on multiple lines" in {
      val features = "A,B,C,D,E,F".toFs
      val deps = Set(Dependency("A".toF,"D".toF), Dependency("B".toF, "C".toF),
                     Dependency("D".toF, "E".toF), Dependency("E".toF, "B".toF))

      FeatureParser.parseItem("(A,B,C)[A->D,B->C]\n(D,E,F)[D->E,E->B]\n") mustEqual Some(features,deps)
    }
  }
}
