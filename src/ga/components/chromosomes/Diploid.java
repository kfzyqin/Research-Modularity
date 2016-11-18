package ga.components.chromosomes;

import com.sun.istack.internal.NotNull;
import ga.components.materials.Material;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.ArrayList;

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
 * This class represents a chromosomes with genotype that comprises two strands of genetic genotype.
 * It requires a dominance mapping for genotype-to-phenotype mapping.
 * The length of each Material must agree. The mapping can be changed during runtime.
 *
 * @author Siu Kei Muk (David)
 * @since 2/09/16.
 */
public abstract class Diploid<M extends Material, P extends Material> extends Chromosome<M, P>{

    /**
     * Constructs a diploid object.
     *
     * @param strand1 first strand of genetic material
     * @param strand2 second strand of genetic material
     * @param mapping genotype-to-phenotype mapping
     */
    public Diploid(@NotNull final M strand1,
                   @NotNull final M strand2,
                   @NotNull final ExpressionMap<M, P> mapping) {
        super(2, strand1.getSize(), mapping);
        if (strand2.getSize() != length)
            throw new IllegalArgumentException("Size of strands does not agree.");
        genotype = new ArrayList<>(2);
        genotype.add(strand1);
        genotype.add(strand2);
    }
}
