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
public class SimpleGenderHotspotState<G extends Chromosome & CoupleableWithHotspot> extends State<G>
        implements DiploidState<G> {

    protected double reproductionRate;

    /**
     * Constructs an initial state for the GA
     *
     * @param population           initial population
     * @param fitnessFunction      fitness function
     * @param mutator              mutators operator
     * @param reproducer           reproducers operator
     * @param selector             parents selector
     * @param numOfMates           number of parents per reproduction
     * @param expressionMapMutator
     * @param hotspotMutator
     */
    public SimpleGenderHotspotState(
            Population<G> population,
            FitnessFunction fitnessFunction,
            Mutator mutator,
            Reproducer<G> reproducer,
            Selector<G> selector,
            final int numOfMates,
            final double reproductionRate,
            ExpressionMapMutator expressionMapMutator,
            HotspotMutator hotspotMutator) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates);
    }

    @Override
    public void mutateExpressionMap() {

    }

    @Override
    public void reproduce() {

    }

    @Override
    public void mutate() {

    }
}
