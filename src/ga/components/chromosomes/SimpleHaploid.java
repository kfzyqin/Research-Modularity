package ga.components.chromosomes;

import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;
import ga.operations.expressionMaps.ProjectionMap;

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
 * This is a simple implementation of Haploid. The genotype is the same as the phenotype.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public class SimpleHaploid extends Haploid<SimpleMaterial, SimpleMaterial> {

    private static final ExpressionMap<SimpleMaterial,SimpleMaterial> identityMapping = new ProjectionMap<>(0);

    /**
     * Constructs a simple haploid. The mapping is the identity map.
     * @param simpleMaterial a single strand of DNA
     */
    public SimpleHaploid(SimpleMaterial simpleMaterial) {
        super(simpleMaterial, identityMapping);
    }

    @Override
    public SimpleMaterial getPhenotype(final boolean recompute) {
        return genotype.get(0);
    }

    @Override
    public SimpleHaploid copy() {
        return new SimpleHaploid(genotype.get(0).copy());
    }

    @Override
    public String toString() {
        return "Genotype/Phenotype: " + genotype.get(0).toString();
    }
}
