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
public class SimpleGenderDiploid<V> extends SimpleDiploid {

    protected final boolean masculine;
    protected final Hotspot<V> hotspot;
    // protected final List<Double> femaleHotspot;

    public SimpleGenderDiploid(@NotNull final SimpleDNA dna1,
                               @NotNull final SimpleDNA dna2,
                               @NotNull final DominanceMapping<SimpleDNA, SimpleDNA> mapping,
                               @NotNull final Hotspot<V> hotspot,
                               // @NotNull final List<Double> maleHotspot,
                               // @NotNull final List<Double> femaleHotspot,
                               final boolean masculine) {

        super(dna1, dna2, mapping);
        this.masculine = masculine;
        this.hotspot = hotspot;
        // this.maleHotspot = new ArrayList<>(maleHotspot);
        // this.femaleHotspot = new ArrayList<>(femaleHotspot);
    }

    public boolean isMasculine(){
        return masculine;
    }

    /*
    public List<Double> getMaleHotspot(){
        return Collections.unmodifiableList(maleHotspot);
    }

    public List<Double> getFemaleHotspot(){
        return Collections.unmodifiableList(femaleHotspot);
    }
    */

    public Hotspot getHotspot() {
        return hotspot;
        // return (masculine) ? Collections.unmodifiableList(maleHotspot) : Collections.unmodifiableList(femaleHotspot);
    }

    @Override
    public SimpleGenderDiploid copy() {
        SimpleDNA dna1 = materials.get(0).copy();
        SimpleDNA dna2 = materials.get(1).copy();
        DominanceMapping mapping = super.mapping.copy();
        Hotspot<V> hotspotCopy = hotspot.copy();
        return new SimpleGenderDiploid(dna1, dna2, mapping, hotspotCopy, masculine);
        // List<Double> mMap = new ArrayList<>(maleHotspot);
        // List<Double> fMap = new ArrayList<>(femaleHotspot);
        // return new SimpleGenderDiploid(dna1, dna2, mapping, mMap, fMap, masculine);
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
