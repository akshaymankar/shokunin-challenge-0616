
object DependencyResolver {
  sealed trait DependencyResolution
  case class ResolutionSuccess(executionSet: List[Set[Feature]]) extends DependencyResolution
  object ResolutionFailure extends DependencyResolution

  def resolve(features: Set[Feature], dependencies: Set[Dependency]) = {
    if(dependencies.map(_.to).diff(features).isEmpty)
      ResolutionSuccess(resolveLoop(features, dependencies, List())._3)
    else
      ResolutionFailure
  }

  private def resolveLoop(features: Set[Feature],
                          dependencies: Set[Dependency],
                          executionOrder: List[Set[Feature]]): (Set[Feature], Set[Dependency], List[Set[Feature]]) = {
    if(features.isEmpty)
      return (features, dependencies, executionOrder)

    val independentFeatures   = features diff dependencies.map(_.from)
    val remainingFeatures     = features diff independentFeatures
    val remainingDependencies = dependencies.filterNot(d => independentFeatures.contains(d.to))
    val updatedExecutionOrder = executionOrder.:+(independentFeatures)

    resolveLoop(remainingFeatures, remainingDependencies, updatedExecutionOrder)
  }
}
