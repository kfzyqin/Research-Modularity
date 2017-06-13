package ga.components.chromosomes;

import com.sun.istack.internal.NotNull;
import ga.components.materials.GeneRegulatoryNetwork;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

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
 * This class implements a simple diploid that uses SimpleMaterial class as genotype and phenotype.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public class SimpleDiploid extends Diploid<SimpleMaterial, SimpleMaterial>{

    public SimpleDiploid(@NotNull final SimpleMaterial dna1,
                         @NotNull final SimpleMaterial dna2,
                         @NotNull final ExpressionMap<SimpleMaterial, SimpleMaterial> mapping) {
        super(dna1, dna2, mapping);
    }

    @Override
    public SimpleDiploid copy() {
        return new SimpleDiploid(genotype.get(0).copy(), genotype.get(1).copy(), mapping.copy());
    }

    @Override
    public String toString() {
        return "\nDNA_1: " + genotype.get(0).toString() +
                "\nDNA_2: " + genotype.get(1).toString() +
                "\nDominance:\n" + mapping.toString() +
                "\nPhenotype: " + getPhenotype(false).toString();

    }
}
