package ga.operations.initializers;

import ga.collections.Population;
import ga.components.chromosomes.Chromosome;

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
 * This interface provides an abstraction for the (random) initializer of population.
 * It is recommended to use a GeneFactory to generate random gene.
 *
 * @author Siu Kei Muk (David)
 * @since 29/08/16.
 */
public interface Initializer<C extends Chromosome> {
    /**
     * Returns the size of population to be initialized.
     * @return size of population
     */
    int getSize();

    /**
     * Sets the size of population to be initialized.
     * @param size size of population
     */
    void setSize(final int size);

    /**
     * (Random) Initialization of the initial population
     * @return Randomly initialized population
     */
    Population<C> initialize();
}
