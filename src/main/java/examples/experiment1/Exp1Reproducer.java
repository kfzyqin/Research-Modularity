package examples.experiment1;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;
import ga.operations.reproducers.Reproducer;

import java.util.ArrayList;
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
 *
 * @author Siu Kei Muk (David)
 * @since 31/08/16.
 */
public class Exp1Reproducer implements Reproducer<SimpleHaploid> {

    public Exp1Reproducer() {
    }

    @Override
    public List<SimpleHaploid> reproduce(@NotNull List<SimpleHaploid> mates) {
        List<SimpleHaploid> children = new ArrayList<>(2);

        SimpleHaploid child1 = mates.get(0).copy();
        SimpleHaploid child2 = mates.get(1).copy();

        SimpleMaterial dna1 = child1.getMaterialsView().get(0);
        SimpleMaterial dna2 = child2.getMaterialsView().get(0);

        final int length = child1.getLength();
        final int crossIndex = ThreadLocalRandom.current().nextInt(1,length-1);

        for (int i = crossIndex; i < length; i++) {
            final int i1 = ((Gene<Integer>) dna1.getGene(i)).getValue();
            final int i2 = ((Gene<Integer>) dna2.getGene(i)).getValue();
            dna1.getGene(i).setValue(i2);
            dna2.getGene(i).setValue(i1);
        }

        children.add(child1);
        children.add(child2);

        return children;
    }
}
