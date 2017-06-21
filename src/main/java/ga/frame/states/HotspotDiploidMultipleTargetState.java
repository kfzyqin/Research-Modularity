package ga.frame.states;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.WithHotspot;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

/**
 * Created by zhenyueqin on 21/6/17.
 */
public abstract class HotspotDiploidMultipleTargetState<C extends Chromosome & WithHotspot> extends DiploidMultipleTargetState<C> {
    protected HotspotMutator hotspotMutator;

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
     * @param hotspotMutator       hotspot mutator
     */
    public HotspotDiploidMultipleTargetState(
            @NotNull Population<C> population,
            @NotNull FitnessFunction fitnessFunction,
            @NotNull Mutator mutator,
            @NotNull Reproducer<C> reproducer,
            @NotNull Selector<C> selector,
            int numOfMates,
            ExpressionMapMutator expressionMapMutator,
            HotspotMutator hotspotMutator) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates, expressionMapMutator);
        this.hotspotMutator = hotspotMutator;
    }

    public abstract void mutateHotspot();
}
