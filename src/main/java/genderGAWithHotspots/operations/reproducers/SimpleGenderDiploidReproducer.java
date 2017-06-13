package genderGAWithHotspots.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;
import genderGAWithHotspots.components.chromosomes.SimpleGenderDiploid;
import genderGAWithHotspots.components.hotspots.Hotspot;

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
 * This class performs recombination by swapping gene values directly at each gene position on a
 * probability specified by the corresponding recombination rate in the Hotspot object.
 *
 * @author Siu Kei Muk (David)
 * @since 29/09/16.
 */
public class SimpleGenderDiploidReproducer extends GenderReproducer<SimpleGenderDiploid<Integer>> {

    public SimpleGenderDiploidReproducer(final int numOfChildren) {
        super(numOfChildren);
    }

    @Override
    protected SimpleGenderDiploid<Integer> recombine(@NotNull final List<SimpleGenderDiploid<Integer>> mates) {
        SimpleGenderDiploid<Integer> father = mates.get(0);
        SimpleGenderDiploid<Integer> mother = mates.get(1);
        List<Material> maleGametes = crossover(father);
        List<Material> femaleGametes = crossover(mother);
        final int maleMatch = ThreadLocalRandom.current().nextInt(maleGametes.size());
        final int femaleMatch = ThreadLocalRandom.current().nextInt(femaleGametes.size());
        final boolean masculine = ThreadLocalRandom.current().nextBoolean();
        final Hotspot<Integer> hotspot = (Hotspot<Integer>) ((masculine) ? father.getHotspot().copy() : mother.getHotspot().copy());
        final ExpressionMap<SimpleMaterial, SimpleMaterial> mapping = (masculine) ? father.getMapping().copy() : mother.getMapping().copy();
        return new SimpleGenderDiploid<>((SimpleMaterial) maleGametes.get(maleMatch),
                                         (SimpleMaterial) femaleGametes.get(femaleMatch),
                                          mapping, hotspot, masculine);
    }
}
