package ga.operations.priorOperators;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.Coupleable;

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
 * This class implements an elitism operation for Coupleable individuals. The same amount of elites
 * from each gender will be selected to survive to the next generation.
 *
 * @author Siu Kei Muk (David)
 * @since 9/10/16.
 */
public class SimpleGenderElitismOperator<G extends Chromosome & Coupleable> implements PriorOperator<G> {

    private int numOfElitesPerGender;

    public SimpleGenderElitismOperator(final int numOfElitesPerGender) {
        filter(numOfElitesPerGender);
        this.numOfElitesPerGender = numOfElitesPerGender;
    }

    private void filter(final int numOfElitesPerGender) {
        if (numOfElitesPerGender < 1)
            throw new IllegalArgumentException("Number of elites must be a positive integer.");
    }

    @Override
    public void preOperate(@NotNull final Population<G> population) {
        if (numOfElitesPerGender > population.getSize()/2) return;
        List<Individual<G>> individuals = population.getIndividualsView();
        int maleCount = 0;
        int femaleCount = 0;
        Individual<G> individual;
        for (int i = 0; i < individuals.size(); i++) {
            individual = individuals.get(i);
            if (maleCount < numOfElitesPerGender && individual.getChromosome().isMasculine()) {
                population.addCandidate(individual.copy());
                population.markSurvivedIndex(i);
                maleCount++;
            } else if (femaleCount < numOfElitesPerGender && !individual.getChromosome().isMasculine()) {
                population.addCandidate(individual.copy());
                population.markSurvivedIndex(i);
                femaleCount++;
            }
            if (femaleCount >= numOfElitesPerGender && maleCount >= numOfElitesPerGender) return;
        }
    }
}
