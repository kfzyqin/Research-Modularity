package ga.frame.states;

import ga.collections.Individual;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.CoupleableWithHotspot;
import ga.components.chromosomes.WithHotspot;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

import java.util.List;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public class SimpleGenderHotspotState<G extends Chromosome & CoupleableWithHotspot> extends SimpleDiploidState<G>
        implements HotspotState<G> {

    HotspotMutator hotspotMutator;
    /**
     * Constructs an initial state for the GA
     *
     * @param population           initial population
     * @param fitnessFunction      fitness function
     * @param mutator              mutators operator
     * @param reproducer           reproducers operator
     * @param selector             parents selector
     * @param numOfMates           number of parents per reproduction
     * @param reproductionRate
     * @param expressionMapMutator
     */
    public SimpleGenderHotspotState(Population<G> population,
                                    FitnessFunction fitnessFunction,
                                    Mutator mutator,
                                    Reproducer<G> reproducer,
                                    Selector<G> selector,
                                    int numOfMates,
                                    double reproductionRate,
                                    ExpressionMapMutator expressionMapMutator,
                                    HotspotMutator hotspotMutator) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates, reproductionRate, expressionMapMutator);
        this.hotspotMutator = hotspotMutator;
    }

    @Override
    public void mutateHotspot() {
        if (hotspotMutator == null) return;
        for (Individual<G> individual : population.getOffspringPoolView())
            hotspotMutator.mutate(individual.getChromosome().getHotspot());
    }
}
