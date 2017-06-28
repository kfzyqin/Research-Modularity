package ga.frame.states;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.CoupleableWithHotspot;
import ga.components.chromosomes.WithHotspot;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

/**
 * Created by zhenyueqin on 17/6/17.
 * Todo: not finished yet.
 */
public abstract class GenderHotspotMultipleTargetState<G extends Chromosome & CoupleableWithHotspot> extends DiploidMultipleTargetState<G> {

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
     */
    public GenderHotspotMultipleTargetState(
            @NotNull Population<G> population,
            @NotNull FitnessFunction fitnessFunction,
            @NotNull Mutator mutator,
            @NotNull Reproducer<G> reproducer,
            @NotNull Selector<G> selector,
            final int numOfMates,
            ExpressionMapMutator expressionMapMutator,
            HotspotMutator hotspotMutator) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates, expressionMapMutator);
        this.hotspotMutator = hotspotMutator;
    }

    public abstract void mutateHotspot();
}