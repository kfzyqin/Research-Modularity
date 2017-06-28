package ga.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.components.genes.Gene;
import ga.components.materials.Material;
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
 * This class is an implementation of a simple random reproducers for simple diploids.
 * The match probability determines the likelihood of choosing combination over the other in the pairing part.
 * The gene value swapping is performed after chromosomes pairing.
 *
 * @author Siu Kei Muk (David) and Zhenyue Qin
 * @since 8/09/16.
 */
public class SimpleDiploidReproducer extends DiploidReproducer<SimpleDiploid> {

    public SimpleDiploidReproducer() {
        super();
    }

    public SimpleDiploidReproducer(final double matchProbability) {
        super(matchProbability);
    }

    public SimpleDiploidReproducer(final double matchProbability, final boolean toDoCrossover) {
        super(matchProbability, toDoCrossover);
    }

    @Override
    protected List<SimpleDiploid> recombine(List<SimpleDiploid> mates) {
        List<SimpleDiploid> rtn = new ArrayList<>();

        SimpleDiploid parent1 = mates.get(0);
        SimpleDiploid parent2 = mates.get(1);

        List<SimpleMaterial> parent1Gametes = crossover(mates.get(0));
        List<SimpleMaterial> parent2Gametes = crossover(mates.get(1));

        SimpleMaterial dna1_1 = parent1Gametes.get(0).copy();
        SimpleMaterial dna1_2 = parent1Gametes.get(0).copy();
        SimpleMaterial dna2_1 = parent2Gametes.get(1).copy();
        SimpleMaterial dna2_2 = parent2Gametes.get(1).copy();

        ExpressionMap mapping1 = parent1.getMapping().copy();
        ExpressionMap mapping2 = parent2.getMapping().copy();

        if (Math.random() > matchProbability) {
            SimpleMaterial tmp = dna1_2;
            dna1_2 = dna2_2;
            dna2_2 = tmp;
        }

        if (Math.random() > matchProbability) {
            ExpressionMap tmp = mapping1;
            mapping1 = mapping2;
            mapping2 = tmp;
        }
        rtn.add(new SimpleDiploid(dna1_1, dna1_2, mapping1.copy()));
        rtn.add(new SimpleDiploid(dna2_1, dna2_2, mapping2.copy()));
        return rtn;
    }

}
