package ga.frame.states;

import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.FitnessFunctionMultipleTargets;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

/**
 * Created by zhenyueqin on 19/6/17.
 */
public abstract class MultipleTargetState<C extends Chromosome> extends DiploidMultipleTargetState<C> {

    /**
     * Constructs an initial state for the GA
     *
     * @param population           initial population
     * @param fitnessFunction      fitness function
     * @param mutator              mutators operator
     * @param reproducer           reproducers operator
     * @param selector             parents selector
     * @param numOfMates           number of parents per reproduction
     * @param expressionMapMutator expression map mutator
     */
    public MultipleTargetState(Population<C> population, FitnessFunction fitnessFunction, Mutator mutator, Reproducer<C> reproducer, Selector<C> selector, int numOfMates, ExpressionMapMutator expressionMapMutator) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates, expressionMapMutator);
    }

    public void evaluateWithMultipleTargets(final boolean recomputePhenotype) {
        population.evaluate((FitnessFunctionMultipleTargets) fitnessFunction, recomputePhenotype, this.generation);
    }
}
