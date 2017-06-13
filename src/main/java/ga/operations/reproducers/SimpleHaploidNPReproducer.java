package ga.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.genes.Gene;
import ga.components.materials.Material;

import java.util.ArrayList;
import java.util.Collections;
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
 * This class is a simple implementation for haploid N-point reproducers for simple haploids.
 * The match probability determines the likelihood of choosing combination over the other in the pairing part.
 * The gene value swapping is performed after chromosomes pairing.
 *
 * @author Siu Kei Muk (David)
 * @since 8/09/16.
 */
public class SimpleHaploidNPReproducer implements Reproducer<SimpleHaploid> {

    private final int point;

    public SimpleHaploidNPReproducer(final int point) {
        filter(point);
        this.point = point;
    }

    private void filter(final int point) {
        if (point < 1)
            throw new IllegalArgumentException("Invalid input values detected.");
    }

    @Override
    public List<SimpleHaploid> reproduce(@NotNull List<SimpleHaploid> mates) {
        SimpleHaploid hap1 = mates.get(0).copy();
        SimpleHaploid hap2 = mates.get(1).copy();
        final int length = hap1.getLength();
        if (length-1 < point)
            throw new IllegalArgumentException("Length must be larger than crossover points.");
        Material material1 = hap1.getMaterialsView().get(0);
        Material material2 = hap1.getMaterialsView().get(1);
        List<Integer> crossoverPoints = generateCrossoverPoints(length);
        int index = 0;
        int crossIndex = crossoverPoints.get(0);
        boolean swap = false;

        for (int i = 0; i < length; i++){
            if (i == crossIndex) {
                swap = !swap;
                index++;
                crossIndex = (index < point) ? crossoverPoints.get(index) : length;
            }
            if (swap) {
                Gene gene1 = material1.getGene(i);
                Gene gene2 = material2.getGene(i);
                Object value1 = gene1.getValue();
                gene1.setValue(gene2.getValue());
                gene2.setValue(value1);
            }
        }

        List<SimpleHaploid> children = new ArrayList<>(2);
        children.add(hap1);
        children.add(hap2);
        return children;
    }

    private List<Integer> generateCrossoverPoints(final int length) {
        List<Integer> indices = new ArrayList<>(point);
        while (indices.size() < point) {
            final int index = ThreadLocalRandom.current().nextInt(1,length);
            if (!indices.contains(index))
                indices.add(index);
        }
        Collections.sort(indices);
        return indices;
    }
}
