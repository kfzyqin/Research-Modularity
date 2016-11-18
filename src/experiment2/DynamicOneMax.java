package experiment2;

import com.sun.istack.internal.NotNull;
import ga.components.genes.BinaryGene;
import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;

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
 * @since 2/09/16.
 */
public class DynamicOneMax implements FitnessFunction<SimpleMaterial> {

    private SimpleMaterial mask;
    private double flipProb;
    private final int length;

    public DynamicOneMax(final int length, final double flipProb) {
        if (flipProb > 1 || flipProb < 0)
            throw new IllegalArgumentException("Bit-flipping probability must be between 0 and 1.");
        this.flipProb = flipProb;
        this.length = length;
        List<Gene<Integer>> strand = new ArrayList<>(length);
        for (int i = 0; i < length; i++)
            strand.add(new BinaryGene(0));
        mask = new SimpleMaterial(strand);
    }

    public SimpleMaterial getMask() {
        return mask;
    }

    private void flipGene(BinaryGene gene) {
        gene.setValue(gene.getValue() ^ 1);
    }

    @Override
    public void update() {
        for (int i = 0; i < length; i++)
            if (Math.random() < flipProb)
                flipGene((BinaryGene) mask.getGene(i));
    }

    @Override
    public double evaluate(@NotNull SimpleMaterial phenotype) {
        double fitness = 0;
        for (int i = 0; i < length; i++)
            fitness += (int) phenotype.getGene(i).getValue() ^ (int) mask.getGene(i).getValue();
        return fitness;
    }
}
