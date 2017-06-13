package ga.operations.selectionOperators.selectors;

import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;

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
 * This interface abstracts the selection of parents from a given list of individuals.
 * The given list of individuals is assumed to be sorted in descending order according to fitnessFunctions function value.
 * Before a selection can be performed, the client code must provide the sorted individuals of the current generation
 * for efficiency reason. The selection can be done by calling the 'select' method.
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public interface Selector<C extends Chromosome> {
    /**
     * @param numOfMates number of parents of one reproducers (production of offspring)
     * @return parents for reproducers
     */
    List<C> select(final int numOfMates);

    /**
     * @param individuals list of individuals of the current generation sorted in descending order
     */
    void setSelectionData(List<Individual<C>> individuals);
}
