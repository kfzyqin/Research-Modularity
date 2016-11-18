package ga.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.genes.Gene;
import ga.components.materials.Material;

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
 * This class is a simple implementation for simple haploid random reproducers for simple haploids.
 * The match probability determines the likelihood of choosing combination over the other in the pairing part.
 * The gene value swapping is performed after chromosomes pairing.
 *
 * @author Siu Kei Muk (David)
 * @since 8/09/16.
 */
public class SimpleSimpleHaploidReproducer implements Reproducer<SimpleHaploid> {

    private double probability;

    public SimpleSimpleHaploidReproducer(final double probability) {
        filter(probability);
        this.probability = probability;
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    @Override
    public List<SimpleHaploid> reproduce(@NotNull final List<SimpleHaploid> mates) {
        if (mates.size() != 2)
            throw new IllegalArgumentException("The input must consists of exactly 2 chromosomes.");
        SimpleHaploid hap1 = mates.get(0).copy();
        SimpleHaploid hap2 = mates.get(1).copy();
        Material material1 = hap1.getMaterialsView().get(0);
        Material material2 = hap2.getMaterialsView().get(1);
        for (int i = 0; i < hap1.getLength(); i++) {
            if (Math.random() < probability) {
                Gene gene1 = material1.getGene(i);
                Gene gene2 = material2.getGene(i);
                Object value1 = gene1.getValue();
                Object value2 = gene2.getValue();
                gene1.setValue(value2);
                gene2.setValue(value1);
            }
        }
        List<SimpleHaploid> children = new ArrayList<>(2);
        children.add(hap1);
        children.add(hap2);
        return children;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        filter(probability);
        this.probability = probability;
    }
}
