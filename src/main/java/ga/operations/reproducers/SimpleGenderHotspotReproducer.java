package ga.operations.reproducers;

import ga.components.chromosomes.GenderDiploid;
import ga.components.chromosomes.GenderHotspotDiploid;
import ga.components.hotspots.Hotspot;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public class SimpleGenderHotspotReproducer extends GenderHotspotReproducer<GenderHotspotDiploid> {

    public SimpleGenderHotspotReproducer(int numOfChildren) {
        super(numOfChildren);
    }

    @Override
    protected GenderHotspotDiploid recombine(List<GenderHotspotDiploid> mates) {
        // Todo: Ask Bob about creating new hotspots or using existing hotspots
        GenderHotspotDiploid father = mates.get(0);
        GenderHotspotDiploid mother = mates.get(1);
        List<Material> maleGametes = crossover(father);
        List<Material> femaleGametes = crossover(mother);
        final int maleMatch = ThreadLocalRandom.current().nextInt(maleGametes.size());
        final int femaleMatch = ThreadLocalRandom.current().nextInt(femaleGametes.size());
        final boolean masculine = ThreadLocalRandom.current().nextBoolean();
        final Hotspot hotspot;
        final ExpressionMap<SimpleMaterial, SimpleMaterial> dominanceMap;
        if (Math.random() < 0.5) {
            hotspot = father.getHotspot().copy();
        } else {
            hotspot = mother.getHotspot().copy();
        }
        if (Math.random() < 0.5) {
            dominanceMap = father.getMapping().copy();
        } else {
            dominanceMap = mother.getMapping().copy();
        }
        return new GenderHotspotDiploid((SimpleMaterial) maleGametes.get(maleMatch),
                (SimpleMaterial) femaleGametes.get(femaleMatch), dominanceMap, hotspot, masculine);
    }
}
