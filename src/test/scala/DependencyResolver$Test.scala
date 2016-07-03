import org.specs2.mutable.Specification

class DependencyResolver$Test extends Specification with ImplicitHelpers {
  "DependencyResolver#resolve" should {
    "resolve things when there are no dependencies" in {
      val a = Feature("A")
      val b = Feature("B")
      val c = Feature("C")
      val features = Set(a,b,c)
      val dependencies = Set.empty[Dependency]

      DependencyResolver.resolve(features, dependencies) mustEqual List(Set(a,b,c))
    }

    "resolve things when there are simple dependencies" in {
      val a = Feature("A")
      val b = Feature("B")
      val c = Feature("C")
      val features = Set(a,b,c)
      val dependencies = Set(Dependency(a,b), Dependency(b,c))

      DependencyResolver.resolve(features, dependencies) mustEqual List(Set(c), Set(b), Set(a))
    }

    "resolve the example" in {
      val group1 = FeatureParser.parseItem("(A,B,C,G,H)[G->A,H->A,H->B]").getOrElse((Set.empty[Feature], Set.empty[Dependency]))
      val group2 = FeatureParser.parseItem("(D,E,F,I,J)[I->D,I->E,J->F,J->I,I->H]").getOrElse((Set.empty[Feature], Set.empty[Dependency]))

      val expectedOrder = List("A,B,C,D,E,F".toFs, "G,H".toFs, "I".toFs, "J".toFs)

      DependencyResolver.resolve(group1._1 ++ group2._1, group1._2 ++ group2._2) mustEqual expectedOrder
    }
  }
}
