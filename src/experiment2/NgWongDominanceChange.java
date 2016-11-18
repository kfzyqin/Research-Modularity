package experiment2;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.materials.Material;
import ga.frame.State;
import ga.operations.dynamicHandlers.DynamicHandler;

import java.util.ArrayList;
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
 *
 * @author Siu Kei Muk (David)
 * @since 3/09/16.
 */
public class NgWongDominanceChange implements DynamicHandler<SimpleDiploid>{

    private double threshold;
    private int cycleLength;

    public NgWongDominanceChange(final double threshold, final int cycleLength) {
        filter(threshold, cycleLength);
        this.threshold = threshold;
        this.cycleLength = cycleLength;
    }

    private void filter(final double threshold, final int cycleLength) {
        if (threshold < 0 || threshold > 1)
            throw new IllegalArgumentException("Threshold must be between 0 and 1.");
        if (cycleLength < 1)
            throw new IllegalArgumentException("Cycle length must be at least 1.");
    }

    @Override
    public boolean handle(@NotNull final State<SimpleDiploid> state) {
        final int currGen = state.getGeneration();
        if (currGen == 0 || currGen % cycleLength != 0)
            return false;
        state.getFitnessFunction().update();
        List<Double> preValues = extractFitnessValue(state.getPopulation().getIndividualsView());
        state.evaluate(false);
        List<Individual<SimpleDiploid>> individuals = state.getPopulation().getIndividualsView();
        List<Double> postValues = extractFitnessValue(individuals);
        final int length = individuals.get(0).getChromosome().getLength();

        for (int i = 0; i < preValues.size(); i++) {
            final double fitness1 = preValues.get(i);
            final double fitness2 = postValues.get(i);
            if (fitness1 - fitness2 > threshold*fitness1) {
                Material material1 = individuals.get(i).getChromosome().getMaterialsView().get(0);
                Material material2 = individuals.get(i).getChromosome().getMaterialsView().get(1);
                for (int j = 0; j < length; j++) {
                    NgWongSchemeGene gene1 = (NgWongSchemeGene) material1.getGene(j);
                    NgWongSchemeGene gene2 = (NgWongSchemeGene) material2.getGene(j);
                    dominanceChange(gene1);
                    dominanceChange(gene2);
                }
            }
        }
        state.evaluate(true);
        return true;
    }

    private void dominanceChange(NgWongSchemeGene gene) {
        char c = gene.getValue();
        switch (c) {
            case 'i':
                gene.setValue('1');
                return;
            case '1':
                gene.setValue('i');
                return;
            case 'o':
                gene.setValue('0');
                return;
            case '0':
                gene.setValue('o');
        }
    }

    private List<Double> extractFitnessValue(List<Individual<SimpleDiploid>> individuals) {
        final int size = individuals.size();
        List<Double> fitnessValues = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            fitnessValues.add(individuals.get(i).getFitness());
        }
        return fitnessValues;
    }
}
