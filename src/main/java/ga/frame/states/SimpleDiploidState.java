package ga.frame.states;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;
import org.jetbrains.annotations.NotNull;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public class SimpleDiploidState<C extends Chromosome> extends SimpleHaploidState<C> implements DiploidState<C> {

    protected double reproductionRate;
    private ExpressionMapMutator expressionMapMutator;

    /**
     * Constructs an initial state for the GA
     *
     * @param population      initial population
     * @param fitnessFunction fitness function
     * @param mutator         mutators operator
     * @param reproducer      reproducers operator
     * @param selector        parents selector
     * @param numOfMates      number of parents per reproduction
     */
    public SimpleDiploidState(
            @NotNull Population<C> population,
            @NotNull FitnessFunction fitnessFunction,
            @NotNull Mutator mutator,
            @NotNull Reproducer<C> reproducer,
            @NotNull Selector<C> selector,
            final int numOfMates,
            final double reproductionRate,
            ExpressionMapMutator expressionMapMutator) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates, reproductionRate);
        this.expressionMapMutator = expressionMapMutator;
    }

    @Override
    public void mutateExpressionMap() {
        if (expressionMapMutator == null) return;
        for (Individual<C> individual : population.getOffspringPoolView())
            expressionMapMutator.mutate(individual.getChromosome().getMapping());
    }
}
