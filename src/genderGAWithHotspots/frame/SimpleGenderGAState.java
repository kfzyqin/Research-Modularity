package genderGAWithHotspots.frame;

import com.sun.istack.internal.NotNull;
import ga.operations.mutators.Mutator;
import genderGAWithHotspots.collections.GenderPopulation;
import ga.collections.Individual;
import ga.collections.PopulationMode;
import ga.components.chromosomes.Chromosome;
import genderGAWithHotspots.components.chromosomes.Coupleable;
import ga.operations.fitnessfunction.FitnessFunction;
import genderGAWithHotspots.operations.hotspotMutator.HotspotMutator;
import genderGAWithHotspots.operations.recombinators.CoupleRecombinator;
import genderGAWithHotspots.operations.selectors.CoupleSelector;

import java.util.List;

/**
 * Created by david on 9/10/16.
 */
public class SimpleGenderGAState<G extends Chromosome & Coupleable<H>, H> extends GenderGAState<G, H> {

    protected double recombinationRate;

    public SimpleGenderGAState(@NotNull GenderPopulation<G> population,
                               @NotNull FitnessFunction fitnessFunction,
                               @NotNull Mutator mutator,
                               @NotNull CoupleRecombinator<G> recombinator,
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
    public void recombine() {
        population.setMode(PopulationMode.RECOMBINE);
        selector.setSelectionData(population.getIndividualsView());
        int count = 0;
        final int size = (int) Math.round(population.getSize()*recombinationRate);
        while (count < size && !population.isReady()) {
            List<G> mates = selector.select(numOfMates);
            List<G> children = recombinator.recombine(mates);
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
