package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.GenderPopulation;
import ga.components.chromosome.Chromosome;
import ga.components.chromosome.Coupleable;
import ga.operations.fitnessfunction.FitnessFunction;
import ga.operations.mutator.ChromosomeMutator;
import ga.operations.mutator.HotspotMutator;
import ga.operations.recombinator.CoupleRecombinator;
import ga.operations.selectors.CoupleSelector;

/**
 * Created by david on 9/10/16.
 */
public abstract class GenderGAState<G extends Chromosome & Coupleable<H>, H> extends GAState<G> {

    protected HotspotMutator<H> hotspotMutator;

    public GenderGAState(@NotNull GenderPopulation<G> population,
                         @NotNull FitnessFunction fitnessFunction,
                         @NotNull ChromosomeMutator chromosomeMutator,
                         @NotNull CoupleRecombinator<G> recombinator,
                         @NotNull CoupleSelector<G> selector,
                         HotspotMutator<H> hotspotMutator) {
        super(population, fitnessFunction, chromosomeMutator, recombinator, selector, 2);
        this.hotspotMutator = hotspotMutator;
    }

    public abstract void mutateHotspots();
}
