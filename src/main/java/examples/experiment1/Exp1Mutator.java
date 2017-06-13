package examples.experiment1;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;
import ga.operations.mutators.Mutator;

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
 *
 * @author Siu Kei Muk (David)
 * @since 31/08/16.
 */
public class Exp1Mutator implements Mutator<SimpleHaploid> {

    private double prob;

    public Exp1Mutator(final double prob) {
        if (prob < 0 || prob > 1)
            throw new IllegalArgumentException("Value out of bound");
        this.prob = prob;
    }

    public double getProbability() {
        return prob;
    }

    public void setProbability(final double prob) {
        this.prob = prob;
    }

    @Override
    public void mutate(@NotNull List<Individual<SimpleHaploid>> individuals) {
        for (Individual<SimpleHaploid> h : individuals) {
            SimpleMaterial dna = h.getChromosome().getMaterialsView().get(0);
            for (int i = 0; i < dna.getSize(); i++) {
                if (!toFlip()) continue;
                Gene<Integer> gene = (Gene<Integer>) dna.getGene(i);
                gene.setValue(gene.getValue() ^ 1);
            }
        }
    }

    private boolean toFlip() {
        return prob < Math.random();
    }
}
