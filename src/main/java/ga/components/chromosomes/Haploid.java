package ga.components.chromosomes;

import ga.components.materials.Material;
import ga.operations.expressionMaps.ExpressionMap;
import org.jetbrains.annotations.NotNull;

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
 * This class represents a chromosomes with genotype consisting of exactly one single strand of genetic material.
 * The phenotype is usually the same as the genotype, but not always the case.
 *
 * @author Siu Kei Muk (David)
 * @since 2/09/16.
 */
public abstract class Haploid<M extends Material, P extends Material> extends Chromosome<M, P> {

    /**
     * Constructs a Haploid object.
     * @param strand one single strand of genetic material
     * @param mapping genotype-to-phenotype mapping
     */
    public Haploid(@NotNull final M strand, @NotNull ExpressionMap<M, P> mapping) {
        super(1, strand.getSize(), mapping);
        genotype = new ArrayList<>(1);
        genotype.add(strand);
    }

    @Override
    public String toString() {
        return "Genotype: " + genotype.get(0).toString();
    }
}
