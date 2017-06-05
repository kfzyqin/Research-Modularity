package genderGAWithHotspots.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.collections.PopulationMode;
import ga.components.chromosomes.Chromosome;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.mutators.Mutator;
import genderGAWithHotspots.collections.GenderPopulation;
import genderGAWithHotspots.components.chromosomes.Coupleable;
import genderGAWithHotspots.operations.hotspotMutators.HotspotMutator;
import genderGAWithHotspots.operations.reproducers.CoupleReproducer;
import genderGAWithHotspots.operations.selectors.CoupleSelector;

import java.util.List;

/*
    GASEE is a Java-based genetic algorithm library for scientific exploration and experiment.
    Copyright 2016 Siu-Kei Muk

    This file is part of GASEE.

    GASEE is free library: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 2.1 of the License, or
    (at your option) any later version.

    GASEE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with GASEE.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * This class is almost identical to SimpleState, except that it restricts the individuals to be Coupleable,
 * and every related components and operations to be compatible.
 *
 * @author Siu Kei Muk (David)
 * @since 9/10/16.
 */
public class SimpleGenderState<G extends Chromosome & Coupleable<H>, H> extends GenderState<G, H> {

    protected double reproductionRate;

    public SimpleGenderState(@NotNull GenderPopulation<G> population,
                             @NotNull FitnessFunction fitnessFunction,
                             @NotNull Mutator mutator,
                             @NotNull CoupleReproducer<G> recombinator,
                             @NotNull CoupleSelector<G> selector,
                             HotspotMutator<H> hotspotMutator,
                             final double recombinationRate) {
        super(population, fitnessFunction, mutator, recombinator, selector, hotspotMutator);
        // Todo: Is this a bug?
        setReproductionRate(recombinationRate);
    }

    private void filter(final double reproductionRate) {
        if (reproductionRate < 0 || reproductionRate > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    @Override
    public void reproduce() {
        population.setMode(PopulationMode.REPRODUCE);
        selector.setSelectionData(population.getIndividualsView());
        int count = 0;
        final int size = (int) Math.round(population.getSize()* reproductionRate);
        while (count < size && !population.isReady()) {
            List<G> mates = selector.select(numOfMates);
            List<G> children = reproducer.reproduce(mates);
            population.addCandidateChromosomes(children);
            count += children.size();
        }
    }

    @Override
    public void mutate() {
        mutator.mutate(population.getOffspringPoolView());
    }

    @Override
    public void mutateHotspots() {
        if (hotspotMutator == null) return;
        for (Individual<G> individual : population.getIndividualsView())
            hotspotMutator.mutate(individual.getChromosome().getHotspot());
    }

    public double getReproductionRate() {
        return reproductionRate;
    }

    public void setReproductionRate(final double reproductionRate) {
        filter(reproductionRate);
        this.reproductionRate = reproductionRate;
    }
}
