package ga.components.chromosomes;

import com.sun.istack.internal.NotNull;
import ga.components.hotspots.Hotspot;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhenyueqin on 21/6/17.
 */
public class SimpleHotspotDiploid extends SimpleDiploid implements WithHotspot, Serializable{
    protected Hotspot hotspot;

    public SimpleHotspotDiploid(
            @NotNull SimpleMaterial dna1,
            @NotNull SimpleMaterial dna2,
            @NotNull ExpressionMap<SimpleMaterial, SimpleMaterial> mapping,
            @NotNull final Hotspot hotspot) {
        super(dna1, dna2, mapping);
        this.hotspot = hotspot;
    }

    @Override
    public Hotspot getHotspot() {
        return hotspot;
    }

    @Override
    public SimpleHotspotDiploid copy() {
        SimpleMaterial dna1 = genotype.get(0).copy();
        SimpleMaterial dna2 = genotype.get(1).copy();
        ExpressionMap mapping = super.mapping.copy();
        Hotspot hotspotCopy = hotspot.copy();
        return new SimpleHotspotDiploid(dna1, dna2, mapping, hotspotCopy);
    }

    @Override
    public String toString() {
        return super.toString() + "\nRecombination probability: " + hotspot.toString();
    }


}
