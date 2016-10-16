package genderGAWithHotspots.frame;

import com.sun.istack.internal.NotNull;
import ga.frame.GAState;
import ga.operations.mutators.Mutator;
import genderGAWithHotspots.collections.GenderPopulation;
import ga.components.chromosomes.Chromosome;
import genderGAWithHotspots.components.chromosomes.Coupleable;
import ga.operations.fitnessfunction.FitnessFunction;
import genderGAWithHotspots.operations.hotspotMutator.HotspotMutator;
import genderGAWithHotspots.operations.recombinators.CoupleRecombinator;
import genderGAWithHotspots.operations.selectors.CoupleSelector;

/**
 * Created by david on 9/10/16.
 */
public abstract class GenderGAState<G extends Chromosome & Coupleable<H>, H> extends GAState<G> {

    protected HotspotMutator<H> hotspotMutator;

    public GenderGAState(@NotNull GenderPopulation<G> population,
                         @NotNull FitnessFunction fitnessFunction,
                         @NotNull Mutator mutator,
                         @NotNull CoupleRecombinator<G> recombinator,
                         @NotNull CoupleSelector<G> selector,
                         HotspotMutator<H> hotspotMutator) {
        super(population, fitnessFunction, mutator, recombinator, selector, 2);
        this.hotspotMutator = hotspotMutator;
    }

    public abstract void mutateHotspots();
}
