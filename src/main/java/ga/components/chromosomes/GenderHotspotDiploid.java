package ga.components.chromosomes;

import com.sun.istack.internal.NotNull;
import ga.components.hotspots.Hotspot;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.List;

/**
 * This class is a Coupleable extension of SimpleDiploid.
 *
 * @author Siu Kei Muk (David) and Zhenyue Qin
 * @since 27/08/16.
 */
public class GenderHotspotDiploid extends GenderDiploid implements CoupleableWithHotspot {

    protected Hotspot hotspot;

    public GenderHotspotDiploid(@NotNull final SimpleMaterial dna1,
                                @NotNull final SimpleMaterial dna2,
                                @NotNull final ExpressionMap<SimpleMaterial, SimpleMaterial> mapping,
                                @NotNull final Hotspot hotspot,
                                final boolean masculine) {

        super(dna1, dna2, mapping, masculine);
        this.hotspot = hotspot;
    }

    @Override
    public boolean isMasculine(){
        return masculine;
    }

    @Override
    public Hotspot getHotspot() {
        return hotspot;
    }

    @Override
    public GenderHotspotDiploid copy() {
        SimpleMaterial dna1 = genotype.get(0).copy();
        SimpleMaterial dna2 = genotype.get(1).copy();
        ExpressionMap mapping = super.mapping.copy();
        Hotspot hotspotCopy = hotspot.copy();
        return new GenderHotspotDiploid(dna1, dna2, mapping, hotspotCopy, masculine);
    }

    @Override
    public String toString() {
        List<Double> map = hotspot.getRecombinationRates();
        return super.toString() + ", Recombination probability: " + map.toString();
    }
}
