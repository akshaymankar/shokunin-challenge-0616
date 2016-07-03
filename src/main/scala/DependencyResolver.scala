object DependencyResolver {
  def resolve(features: List[Feature], dependencies: List[Dependency]) = {
    resolveLoop(features, dependencies, List())._3
  }

  private def resolveLoop(features: List[Feature],
                          dependencies: List[Dependency],
                          executionOrder: List[List[Feature]]): (List[Feature], List[Dependency], List[List[Feature]]) = {
    if(features.isEmpty)
      return (features, dependencies, executionOrder)

    val independentFeatures   = features diff dependencies.map(_.from)
    val remainingFeatures     = features diff independentFeatures
    val remainingDependencies = dependencies.filterNot(d => independentFeatures.contains(d.to))
    val updatedExecutionOrder = executionOrder.:+(independentFeatures)

    resolveLoop(remainingFeatures, remainingDependencies, updatedExecutionOrder)
  }
}
