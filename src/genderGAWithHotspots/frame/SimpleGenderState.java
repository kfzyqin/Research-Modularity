package genderGAWithHotspots.frame;

import com.sun.istack.internal.NotNull;
import ga.operations.mutators.Mutator;
import genderGAWithHotspots.collections.GenderPopulation;
import ga.collections.Individual;
import ga.collections.PopulationMode;
import ga.components.chromosomes.Chromosome;
import genderGAWithHotspots.components.chromosomes.Coupleable;
import ga.operations.fitnessFunctions.FitnessFunction;
import genderGAWithHotspots.operations.hotspotMutators.HotspotMutator;
import genderGAWithHotspots.operations.reproducers.CoupleReproducer;
import genderGAWithHotspots.operations.selectors.CoupleSelector;

import java.util.List;

/**
 * Created by david on 9/10/16.
 */
public class SimpleGenderState<G extends Chromosome & Coupleable<H>, H> extends GenderState<G, H> {

    protected double recombinationRate;

    public SimpleGenderState(@NotNull GenderPopulation<G> population,
                             @NotNull FitnessFunction fitnessFunction,
                             @NotNull Mutator mutator,
                             @NotNull CoupleReproducer<G> recombinator,
                             @NotNull CoupleSelector<G> selector,
                             HotspotMutator<H> hotspotMutator,
                             final double recombinationRate) {
        super(population, fitnessFunction, mutator, recombinator, selector, hotspotMutator);
        setRecombinationRate(recombinationRate);
    }

    private void filter(final double recombinationRate) {
        if (recombinationRate < 0 || recombinationRate > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    @Override
    public void reproduce() {
        population.setMode(PopulationMode.REPRODUCE);
        selector.setSelectionData(population.getIndividualsView());
        int count = 0;
        final int size = (int) Math.round(population.getSize()*recombinationRate);
        while (count < size && !population.isReady()) {
            List<G> mates = selector.select(numOfMates);
            List<G> children = reproducer.reproduce(mates);
            population.addCandidateChromosomes(children);
            count += children.size();
        }
    }

    @Override
    public void mutate() {

    }

    @Override
    public void mutateHotspots() {
        if (hotspotMutator == null) return;
        for (Individual<G> individual : population.getIndividualsView())
            hotspotMutator.mutate(individual.getChromosome().getHotspot());
    }

    public double getRecombinationRate() {
        return recombinationRate;
    }

    public void setRecombinationRate(final double recombinationRate) {
        filter(recombinationRate);
        this.recombinationRate = recombinationRate;
    }
}
