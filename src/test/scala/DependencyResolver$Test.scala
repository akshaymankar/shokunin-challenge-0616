import org.specs2.mutable.Specification

class DependencyResolver$Test extends Specification {
  "DependencyResolver#resolve" should {
    "resolve things when there are no dependencies" in {
      val a = Feature("A")
      val b = Feature("B")
      val c = Feature("C")
      val features = List(a,b)
      val dependencies = List()

      DependencyResolver.resolve(features, dependencies) mustEqual List(List(a,b,c))
    }

    "resolve things when there are simple dependencies" in {
      val a = Feature("A")
      val b = Feature("B")
      val c = Feature("C")
      val features = List(a,b,c)
      val dependencies = List(Dependency(a,b), Dependency(b,c))

      DependencyResolver.resolve(features, dependencies) mustEqual List(List(c), List(b), List(a))
    }

    implicit class FeatureString(s: String) {
      def toF: Feature = Feature(s)
      def toFs: List[Feature] = s.split(",").map(_.toF).toList
    }

    "resolve the example" in {
      val group1 = FeatureParser.parseItem("(A,B,C,G,H)[G->A,H->A,H->B]").getOrElse((List(), List()))
      val group2 = FeatureParser.parseItem("(D,E,F,I,J)[I->D,I->E,J->F,J->I,I->H]").getOrElse((List(), List()))

      val expectedOrder = List("A,B,C,D,E,F".toFs, "G,H".toFs, "I".toFs, "J".toFs)

      DependencyResolver.resolve(group1._1 ++ group2._1, group1._2 ++ group2._2) mustEqual expectedOrder
    }
  }
}
