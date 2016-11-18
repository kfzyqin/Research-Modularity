package experiment2;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.genes.Gene;
import ga.components.materials.Material;
import ga.operations.mutators.Mutator;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
 * @since 12/09/16.
 */
public class RyanAdditiveMutator implements Mutator<SimpleDiploid> {

    private static final char[] values = {'A','B','C','D'};
    private double probability;

    public RyanAdditiveMutator(double probability) {
        setProbability(probability);
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        filter(probability);
        this.probability = probability;
    }

    @Override
    public void mutate(@NotNull List<Individual<SimpleDiploid>> individuals) {
        for (Individual<SimpleDiploid> individual : individuals) {
            Material material1 = individual.getChromosome().getMaterialsView().get(0);
            Material material2 = individual.getChromosome().getMaterialsView().get(1);
            for (int i = 0; i < material1.getSize(); i++) {
                if (Math.random() < probability) {
                    Gene gene = material1.getGene(i);
                    gene.setValue(generateValue((char)gene.getValue()));
                }

                if (Math.random() < probability) {
                    Gene gene = material2.getGene(i);
                    gene.setValue(generateValue((char)gene.getValue()));
                }
            }
        }
    }

    private char generateValue(final char invalidValue) {
        char value = values[ThreadLocalRandom.current().nextInt(values.length)];
        while (value == invalidValue)
            value = values[ThreadLocalRandom.current().nextInt(values.length)];
        return value;
    }
}
