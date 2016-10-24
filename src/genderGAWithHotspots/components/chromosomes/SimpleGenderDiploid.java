package genderGAWithHotspots.components.chromosomes;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.SimpleDiploid;
import ga.operations.dominanceMaps.DominanceMap;
import genderGAWithHotspots.components.hotspots.Hotspot;
import ga.components.materials.SimpleMaterial;

import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public class SimpleGenderDiploid<V> extends SimpleDiploid implements Coupleable<V>{

    protected final boolean masculine;
    protected final Hotspot<V> hotspot;

    public SimpleGenderDiploid(@NotNull final SimpleMaterial dna1,
                               @NotNull final SimpleMaterial dna2,
                               @NotNull final DominanceMap<SimpleMaterial, SimpleMaterial> mapping,
                               @NotNull final Hotspot<V> hotspot,
                               final boolean masculine) {

        super(dna1, dna2, mapping);
        this.masculine = masculine;
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
    public SimpleGenderDiploid copy() {
        SimpleMaterial dna1 = genotype.get(0).copy();
        SimpleMaterial dna2 = genotype.get(1).copy();
        DominanceMap mapping = super.mapping.copy();
        Hotspot<V> hotspotCopy = hotspot.copy();
        return new SimpleGenderDiploid(dna1, dna2, mapping, hotspotCopy, masculine);
    }

    @Override
    public String toString() {
        String gender = (masculine) ? "M" : "F";
        List<Double> map = hotspot.getRecombinationRate();
        return "Gender: " + gender +
                ", " + super.toString() +
                ", Recombination probability: " + map.toString();
    }
}
