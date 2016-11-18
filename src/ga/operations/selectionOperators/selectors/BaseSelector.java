package ga.operations.selectionOperators.selectors;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.operations.selectionOperators.selectionSchemes.SelectionScheme;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * This class provides a simple framework for implementation of selector.
 * The given reference of list of individuals is stored for efficient retrieval purpose.
 * Any subclass of this class must implement a way that facilitates execution according to particular selection scheme.
 *
 * @author Siu Kei Muk (David)
 * @since 3/09/16.
 */
public abstract class BaseSelector<C extends Chromosome> implements Selector<C> {

    protected List<Individual<C>> individuals;
    protected List<Double> fitnessValues;
    protected SelectionScheme scheme;

    public BaseSelector(@NotNull final SelectionScheme scheme) {
        this.scheme = scheme;
        fitnessValues = new ArrayList<>();
    }

    @Override
    public List<C> select(final int numOfMates) {
        Set<Integer> set = new HashSet<>(numOfMates);
        while (set.size() < numOfMates) {
            set.add(scheme.select(fitnessValues));
        }

        List<C> parents = new ArrayList<>(numOfMates);

        for (int i : set) {
            parents.add(individuals.get(i).getChromosome());
        }
        return parents;
    }
}
