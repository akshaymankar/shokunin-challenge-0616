## Dependency Resolver

This is the solution to the Shokunin Challenge of June 2016.
The goal of this program is to resolve depedencies between features to decide the order of execution.

### Sample Input

    (A,B,C,G,H)[G->A,H->A,H->B]
    (D,E,F,I,J)[I->D,I->E,J->F,J->I,I->H]

where, A,B,C..J are features.
And A->B means A depends on B, i.e., A cannot be executed before B.

### Output for above input

    A,B,C,D,E,F
    G,H
    I
    J

### How to run

    ./sbt run -q

## How to run tests

    ./sbt test
