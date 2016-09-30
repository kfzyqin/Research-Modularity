package ga.components.chromosome;

import com.sun.istack.internal.NotNull;
import ga.components.hotspots.Hotspot;
import ga.components.materials.SimpleDNA;
import ga.operations.dominanceMappings.DominanceMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public class SimpleGenderDiploid<V> extends SimpleDiploid implements Coupleable<V>{

    protected final boolean masculine;
    protected final Hotspot<V> hotspot;

    public SimpleGenderDiploid(@NotNull final SimpleDNA dna1,
                               @NotNull final SimpleDNA dna2,
                               @NotNull final DominanceMapping<SimpleDNA, SimpleDNA> mapping,
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
        SimpleDNA dna1 = materials.get(0).copy();
        SimpleDNA dna2 = materials.get(1).copy();
        DominanceMapping mapping = super.mapping.copy();
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
