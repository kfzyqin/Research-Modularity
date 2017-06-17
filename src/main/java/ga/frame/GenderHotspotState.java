package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.GenderPopulation;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.Coupleable;
import ga.components.chromosomes.CoupleableWithHotspot;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

/**
 * Created by zhenyueqin on 17/6/17.
 * Todo: not finished yet.
 */
public abstract class GenderHotspotState<G extends Chromosome & CoupleableWithHotspot> extends State<G> {

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
    public GenderHotspotState(
            @NotNull GenderPopulation<G> population,
            @NotNull FitnessFunction fitnessFunction,
            @NotNull Mutator mutator,
            @NotNull Reproducer<G> reproducer,
            @NotNull Selector<G> selector,
            int numOfMates) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates);
    }
}
