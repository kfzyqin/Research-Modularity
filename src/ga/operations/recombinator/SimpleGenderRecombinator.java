package ga.operations.recombinator;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.SimpleGenderDiploid;
import ga.components.hotspots.Hotspot;
import ga.components.materials.GeneticMaterial;
import ga.components.materials.SimpleDNA;
import ga.operations.dominanceMappings.DominanceMapping;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 29/09/16.
 */
public class SimpleGenderRecombinator extends GenderRecombinator<SimpleGenderDiploid<Integer>> {

    public SimpleGenderRecombinator(final int numOfChildren) {
        super(numOfChildren);
    }

    @Override
    protected SimpleGenderDiploid<Integer> reproduce(@NotNull final List<SimpleGenderDiploid<Integer>> mates) {
        SimpleGenderDiploid<Integer> father = mates.get(0);
        SimpleGenderDiploid<Integer> mother = mates.get(1);
        List<GeneticMaterial> maleGametes = crossover(father);
        List<GeneticMaterial> femaleGametes = crossover(mother);
        final int maleMatch = ThreadLocalRandom.current().nextInt(maleGametes.size());
        final int femaleMatch = ThreadLocalRandom.current().nextInt(femaleGametes.size());
        final boolean masculine = ThreadLocalRandom.current().nextBoolean();
        final Hotspot<Integer> hotspot = (Hotspot<Integer>) ((masculine) ? father.getHotspot().copy() : mother.getHotspot().copy());
        final DominanceMapping<SimpleDNA, SimpleDNA> mapping = (masculine) ? father.getMapping().copy() : mother.getMapping().copy();
        return new SimpleGenderDiploid<>((SimpleDNA) maleGametes.get(maleMatch),
                                         (SimpleDNA) femaleGametes.get(femaleMatch),
                                          mapping, hotspot, masculine);
    }
}
