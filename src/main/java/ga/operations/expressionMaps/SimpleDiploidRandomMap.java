package ga.operations.expressionMaps;

import com.sun.istack.internal.NotNull;
import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;

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
 * Created by david on 9/10/16.
 */
public class SimpleDiploidRandomMap implements ExpressionMap<SimpleMaterial, SimpleMaterial> {

    private double probability;

    public SimpleDiploidRandomMap(final double probability) {
        filter(probability);
        this.probability = probability;
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1) throw new IllegalArgumentException("Invalid probability value.");
    }

    @Override
    public ExpressionMap<SimpleMaterial, SimpleMaterial> copy() {
        return new SimpleDiploidRandomMap(probability);
    }

    @Override
    public SimpleMaterial map(@NotNull List<SimpleMaterial> materials) {
        SimpleMaterial dna1 = materials.get(0);
        SimpleMaterial dna2 = materials.get(1);
        // SimpleMaterial dna = new SimpleMaterial();
        List<Gene> genes = new ArrayList<>(dna1.getSize());
        for (int i = 0; i < dna1.getSize(); i++) {
            genes.add(i, (Math.random() < probability) ? (Gene) (dna1.getGene(i).copy()) : (Gene) dna2.getGene(i).copy());
        }
        return new SimpleMaterial(genes);
    }

    @Override
    public String toString() {
        return "Random mapping with probability: " + probability;
    }
}
