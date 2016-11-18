package experiment1;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.components.chromosomes.SimpleHaploid;
import ga.operations.mutators.Mutator;
import ga.operations.priorOperators.PriorOperator;
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
 * @author Siu Kei Muk (David)
 * @since 31/08/16.
 */
public class Exp1PriorOperator implements PriorOperator<SimpleHaploid> {

    private int numOfElites;
    private Selector selector;
    private Mutator<SimpleHaploid> mutator = null;

    public Exp1PriorOperator(final int numOfElites, Selector selector) {
        if (numOfElites < 1)
            throw new IllegalArgumentException("Number of elites must be a positive integer.");
        this.numOfElites = numOfElites;
        this.selector = selector;
    }

    @Override
    public void preOperate(@NotNull Population<SimpleHaploid> population) {
        population.setMode(PopulationMode.PRIOR);
        /*
        List<Integer> indices = new ArrayList<>(numOfElites);
        List<Double> fitnessValues = population.getFitnessValuesView();
        List<Individual<SimpleHaploid>> individuals = population.getIndividualsView();
        while (indices.size() < numOfElites) {
            final int index = selector.select(fitnessValues);
            if (!indices.contains(index)) indices.add(index);
        }
        System.out.println(indices);
        */
        List<Individual<SimpleHaploid>> individuals = population.getIndividualsView();
        for (int i = 0; i < 20; i++) population.addCandidate(individuals.get(i));
    }

    private void mutate(@NotNull final List<Individual<SimpleHaploid>> mutant,
                        @NotNull final Mutator<SimpleHaploid> mutator) {
        mutator.mutate(mutant);
    }

    public int getNumOfElites() {
        return numOfElites;
    }

    public void setNumOfElites(int numOfElites) {
        this.numOfElites = numOfElites;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public void setMutator(final Mutator<SimpleHaploid> mutator) {
        this.mutator = mutator;
    }
}
