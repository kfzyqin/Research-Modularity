package ga.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.GenderDiploid;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public class GenderDiploidReproducer extends GenderReproducer<GenderDiploid> {

    public GenderDiploidReproducer(final int numOfChildren, final double dominanceMapMutationRate) {
        super(numOfChildren, dominanceMapMutationRate);
    }

    @Override
    protected GenderDiploid recombine(@NotNull final List<GenderDiploid> mates) {
        GenderDiploid father = mates.get(0);
        GenderDiploid mother = mates.get(1);
        List<Material> maleGametes = crossover(father);
        List<Material> femaleGametes = crossover(mother);
        final int maleMatch = ThreadLocalRandom.current().nextInt(maleGametes.size());
        final int femaleMatch = ThreadLocalRandom.current().nextInt(femaleGametes.size());
        final boolean masculine = ThreadLocalRandom.current().nextBoolean();
        final ExpressionMap<SimpleMaterial, SimpleMaterial> dominanceMap = new DiploidEvolvedMap(father.getLength());
        return new GenderDiploid((SimpleMaterial) maleGametes.get(maleMatch),
                (SimpleMaterial) femaleGametes.get(femaleMatch),
                dominanceMap, masculine);
    }
}
