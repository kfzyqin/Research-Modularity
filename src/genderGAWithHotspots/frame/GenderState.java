package genderGAWithHotspots.frame;

import com.sun.istack.internal.NotNull;
import ga.frame.State;
import ga.operations.mutators.Mutator;
import genderGAWithHotspots.collections.GenderPopulation;
import ga.components.chromosomes.Chromosome;
import genderGAWithHotspots.components.chromosomes.Coupleable;
import ga.operations.fitnessfunction.FitnessFunction;
import genderGAWithHotspots.operations.hotspotMutator.HotspotMutator;
import genderGAWithHotspots.operations.reproducers.CoupleReproducer;
import genderGAWithHotspots.operations.selectors.CoupleSelector;

/**
 * Created by david on 9/10/16.
 */
public abstract class GenderState<G extends Chromosome & Coupleable<H>, H> extends State<G> {

    protected HotspotMutator<H> hotspotMutator;

    public GenderState(@NotNull GenderPopulation<G> population,
                       @NotNull FitnessFunction fitnessFunction,
                       @NotNull Mutator mutator,
                       @NotNull CoupleReproducer<G> recombinator,
                       @NotNull CoupleSelector<G> selector,
                       HotspotMutator<H> hotspotMutator) {
        super(population, fitnessFunction, mutator, recombinator, selector, 2);
        this.hotspotMutator = hotspotMutator;
    }

    public abstract void mutateHotspots();
}
