package experiment2;

import com.sun.istack.internal.NotNull;
import ga.components.genes.BinaryGene;
import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

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
public class RyanAdditiveScheme implements ExpressionMap<SimpleMaterial, SimpleMaterial> {

    public RyanAdditiveScheme() {
    }

    private int toPoints(final char value) {
        switch (value) {
            case 'A':
                return 2;
            case 'B':
                return 3;
            case 'C':
                return 7;
            case 'D':
                return 9;
            default:
                throw new IllegalArgumentException("Invalid value. The permitted values are 'A', 'B', 'C' or 'D'.");
        }
    }

    @Override
    public ExpressionMap<SimpleMaterial, SimpleMaterial> copy() {
        return new RyanAdditiveScheme();
    }

    @Override
    public SimpleMaterial map(@NotNull List<SimpleMaterial> materials) {
        SimpleMaterial dna1 = materials.get(0);
        SimpleMaterial dna2 = materials.get(1);
        final int length = dna1.getSize();
        List<Gene<Integer>> genes = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            final int value1 = toPoints((char)dna1.getGene(i).getValue());
            final int value2 = toPoints((char)dna2.getGene(i).getValue());
            genes.add((value1 + value2 > 10) ? new BinaryGene(1) : new BinaryGene(0));
        }
        return new SimpleMaterial(genes);
    }

    @Override
    public String toString() {
        return "'A':2, 'B':3, 'C':7, 'D':9. Sum > 10: 1, otherwise: 0.";
    }
}
