package ga.frame.states;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.components.chromosomes.Chromosome;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

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
 * This class is a simple implementation of the state. The reproducers rate determines the proportion of
 * individuals in the new generation that are produced by reproducers.
 *
 * @author Siu Kei Muk (David)
 * @since 12/09/16.
 */
public class SimpleState<C extends Chromosome> extends State<C> {

    protected double reproductionRate;

    public SimpleState(@NotNull final Population<C> population,
                       @NotNull final FitnessFunction fitnessFunction,
                       @NotNull final Mutator mutator,
                       @NotNull final Reproducer<C> reproducer,
                       @NotNull final Selector<C> selector,
                       final int numOfMates,
                       final double reproductionRate) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates);
        setReproductionRate(reproductionRate);
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }


    @Override
    public void reproduce() {
        population.setMode(PopulationMode.REPRODUCE);
        selector.setSelectionData(population.getIndividualsView());
        int count = 0;
        final int size = (int) Math.round(population.getSize()* reproductionRate);
        while (count < size && !population.isReady()) {
            List<C> mates = selector.select(numOfMates);
            List<C> children = reproducer.reproduce(mates);
            population.addCandidateChromosomes(children);
            count += children.size();
        }
    }

    @Override
    public void mutate() {
        mutator.mutate(population.getOffspringPoolView());
    }

    public double getReproductionRate() {
        return reproductionRate;
    }

    public void setReproductionRate(final double reproductionRate) {
        filter(reproductionRate);
        this.reproductionRate = reproductionRate;
    }
}
