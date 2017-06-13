package ga.operations.mutators;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.components.genes.BinaryGene;
import ga.components.materials.Material;

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
 * This class implements a mutators operation for chromosomes with pure binary genes.
 *
 * @author Siu Kei Muk (David)
 * @since 3/09/16.
 */
public class BinaryGeneMutator<T extends Chromosome> implements Mutator<T> {

    private double prob;

    /**
     * Constructs a BinaryGeneMutator object.
     * @param prob mutators probability
     */
    public BinaryGeneMutator(final double prob) {
        filter(prob);
        this.prob = prob;
    }

    private void filter(final double prob) {
        if (prob < 0 || prob > 1)
            throw new IllegalArgumentException("Mutation probability must be between 0 and 1.");
    }

    public double getProbability() {
        return prob;
    }

    public void setProbability(final double prob) {
        filter(prob);
        this.prob = prob;
    }

    @Override
    public void mutate(@NotNull List<Individual<T>> individuals) {
        for (int i = 0; i < individuals.size(); i++) {
            Individual<T> individual = individuals.get(i);
            for (Object object :  individual.getChromosome().getMaterialsView()) {
                Material material = (Material) object;
                for (int j = 0; j < material.getSize(); j++) {
                    flipAllele((BinaryGene) material.getGene(j));
                }
            }
        }
    }

    private void flipAllele(BinaryGene gene) {
        if (Math.random() < prob)
            gene.setValue(gene.getValue() ^ 1);
    }

}
