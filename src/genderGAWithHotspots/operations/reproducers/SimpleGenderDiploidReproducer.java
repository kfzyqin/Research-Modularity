package genderGAWithHotspots.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.materials.Material;
import ga.operations.dominanceMaps.DominanceMap;
import genderGAWithHotspots.components.chromosomes.SimpleGenderDiploid;
import genderGAWithHotspots.components.hotspots.Hotspot;
import ga.components.materials.SimpleMaterial;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 29/09/16.
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
        final DominanceMap<SimpleMaterial, SimpleMaterial> mapping = (masculine) ? father.getMapping().copy() : mother.getMapping().copy();
        return new SimpleGenderDiploid<>((SimpleMaterial) maleGametes.get(maleMatch),
                                         (SimpleMaterial) femaleGametes.get(femaleMatch),
                                          mapping, hotspot, masculine);
    }
}
