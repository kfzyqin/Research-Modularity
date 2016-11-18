package ga.operations.selectionOperators.selectionSchemes;

import com.sun.istack.internal.NotNull;

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
 * This interface abstracts the selection scheme of genetic algorithm.
 * The list of individuals and the list of given fitness function values must correspond in order.
 *
 * @author Siu Kei Muk (David)
 * @since 9/09/16.
 */
public interface SelectionScheme {

    /**
     * This method assumes the individuals in the population are sorted in descending order for the
     * returned index to locate the selected individual.
     *
     * @param fitnessValues descending sorted fitness function values of individuals.
     * @return index of the selected individual's index/position.
     */
    int select(@NotNull final List<Double> fitnessValues);
}
