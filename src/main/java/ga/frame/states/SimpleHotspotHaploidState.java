package ga.frame.states;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.WithHotspot;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

/**
 * Created by Zhenyue Qin (秦震岳) on 15/7/17.
 * The Australian National University.
 */
public class SimpleHotspotHaploidState<C extends Chromosome & WithHotspot> extends SimpleHaploidState<C>
        implements HotspotState<C> {
    private HotspotMutator hotspotMutator;

    public SimpleHotspotHaploidState(Population<C> population,
                                     FitnessFunction fitnessFunction,
                                     Mutator mutator,
                                     Reproducer<C> reproducer,
                                     Selector<C> selector,
                                     int numOfMates,
                                     double reproductionRate,
                                     HotspotMutator hotspotMutator) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates, reproductionRate);
        this.hotspotMutator = hotspotMutator;
    }

    @Override
    public void mutateHotspot() {
        if (hotspotMutator == null) return;
        for (Individual<C> individual : population.getOffspringPoolView())
            hotspotMutator.mutate(individual.getChromosome().getHotspot());
    }
}
