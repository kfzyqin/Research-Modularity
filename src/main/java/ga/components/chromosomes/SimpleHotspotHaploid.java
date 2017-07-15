package ga.components.chromosomes;

import com.sun.istack.internal.NotNull;
import ga.components.hotspots.Hotspot;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

/**
 * Created by Zhenyue Qin (秦震岳) on 15/7/17.
 * The Australian National University.
 */
public class SimpleHotspotHaploid extends SimpleHaploid implements WithHotspot {
    protected Hotspot hotspot;

    /**
     * Constructs a simple haploid. The mapping is the identity map.
     *
     * @param simpleMaterial a single strand of DNA
     */
    public SimpleHotspotHaploid(@NotNull SimpleMaterial simpleMaterial, @NotNull final Hotspot hotspot) {
        super(simpleMaterial);
        this.hotspot = hotspot;
    }

    @Override
    public Hotspot getHotspot() {
        return this.hotspot;
    }

    @Override
    public SimpleHotspotHaploid copy() {
        return new SimpleHotspotHaploid(genotype.get(0).copy(), this.hotspot.copy());
    }

    @Override
    public String toString() {
        return super.toString() + "\nRecombination probability: " + hotspot.toString();
    }
}
