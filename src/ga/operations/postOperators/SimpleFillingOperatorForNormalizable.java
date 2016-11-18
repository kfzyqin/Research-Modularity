package ga.operations.postOperators;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.collections.Population;
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
 * This class is an implementation of post-operator that fills up the remaining space by
 * selecting individuals from the current generation to survive in the next generation
 * according to a given selection scheme that requires normalization of fitness function
 * values.
 *
 * @author Siu Kei Muk (David)
 * @since 12/09/16.
 */
public class SimpleFillingOperatorForNormalizable<T extends Chromosome> implements PostOperator<T> {

    private SelectionScheme scheme;

    public SimpleFillingOperatorForNormalizable(@NotNull final SelectionScheme scheme) {
        this.scheme = scheme;
    }

    @Override
    public void postOperate(@NotNull final Population<T> population) {
        Set<Integer> survivedIndices = population.getSurvivedIndicesView();
        Set<Integer> selected = new HashSet<>();
        List<Individual<T>> individuals = population.getIndividualsView();
        List<Double> normalized = normalizeFitness(individuals);
        final int amount = population.getSize() - population.getNextGenSize();
        while (selected.size() < amount) {
            final int index = scheme.select(normalized);
            if (!survivedIndices.contains(index)) selected.add(index);
        }
        for (Integer i : selected) population.addCandidate(individuals.get(i).copy());
    }

    private List<Double> normalizeFitness(List<Individual<T>> individuals) {
        final double sum = individuals.stream().mapToDouble(Individual::getFitness).sum();
        final int size = individuals.size();
        List<Double> normalized = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            normalized.add(individuals.get(i).getFitness()/sum);
        }
        return normalized;
    }
}
