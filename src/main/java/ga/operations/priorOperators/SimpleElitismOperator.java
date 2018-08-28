package ga.operations.priorOperators;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import org.jetbrains.annotations.NotNull;

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
 * This class performs elitism selection before the reproduction process takes place
 * to ensure sufficient vacancies for selected elites.
 *
 * @author Siu Kei Muk (David)
 * @since 11/09/16.
 */
public class SimpleElitismOperator<C extends Chromosome> implements PriorOperator<C>{

    private int numOfElites;

    public SimpleElitismOperator(final int numOfElites) {
        filter(numOfElites);
        this.numOfElites = numOfElites;
    }

    private void filter(final int numOfElites) {
        if (numOfElites < 1)
            throw new IllegalArgumentException("Number of elites must be a positive integer.");
    }

    @Override
    public void preOperate(@NotNull Population<C> population) {
        List<Individual<C>> individuals = population.getIndividualsView();
        for (int i = 0; i < numOfElites; i++) {
            population.addCandidate(individuals.get(i).copy());
            population.markSurvivedIndex(i);
        }
    }
}
