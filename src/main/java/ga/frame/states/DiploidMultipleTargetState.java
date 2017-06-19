package ga.frame.states;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.FitnessFunctionWithMultipleTargets;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public abstract class DiploidMultipleTargetState<C extends Chromosome> extends State<C> {

    protected ExpressionMapMutator expressionMapMutator;

    /**
     * Constructs an initial state for the GA
     *
     * @param population            initial population
     * @param fitnessFunction       fitness function
     * @param mutator               mutators operator
     * @param reproducer            reproducers operator
     * @param selector              parents selector
     * @param numOfMates            number of parents per reproduction
     * @param expressionMapMutator  expression map mutator
     */
    public DiploidMultipleTargetState(
            @NotNull Population<C> population,
            @NotNull FitnessFunction fitnessFunction,
            @NotNull Mutator mutator,
            @NotNull Reproducer<C> reproducer,
            @NotNull Selector<C> selector,
            final int numOfMates,
            @NotNull ExpressionMapMutator expressionMapMutator) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates);
        this.expressionMapMutator = expressionMapMutator;
    }

    public abstract void mutateExpressionMap();

    public void evaluateWithMultipleTargets(final boolean recomputePhenotype) {
        population.evaluate((FitnessFunctionWithMultipleTargets) fitnessFunction, recomputePhenotype, this.generation);
    }
}
