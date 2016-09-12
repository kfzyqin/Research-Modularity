package ga.components.chromosome;

import com.sun.istack.internal.NotNull;
import ga.components.materials.SimpleDNA;
import ga.operations.dominanceMappings.DominanceMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public final class GenderSimpleDiploid extends SimpleDiploid {

    private final boolean masculine;
    private final List<Double> masRecomMap;
    private final List<Double> femRecomMap;

    public GenderSimpleDiploid(@NotNull final SimpleDNA dna1,
                               @NotNull final SimpleDNA dna2,
                               @NotNull final DominanceMapping<SimpleDNA, SimpleDNA> mapping,
                               @NotNull final List<Double> masRecomMap,
                               @NotNull final List<Double> femRecomMap,
                               final boolean masculine) {

        super(dna1, dna2, mapping);
        this.masculine = masculine;
        this.masRecomMap = new ArrayList<>(masRecomMap);
        this.femRecomMap = new ArrayList<>(femRecomMap);
    }

    public boolean isMasculine(){
        return masculine;
    }

    public List<Double> getMasRecomMap(){
        return Collections.unmodifiableList(masRecomMap);
    }

    public List<Double> getFemRecomMap(){
        return Collections.unmodifiableList(femRecomMap);
    }

    @Override
    public GenderSimpleDiploid copy() {
        SimpleDNA dna1 = materials.get(0).copy();
        SimpleDNA dna2 = materials.get(1).copy();
        DominanceMapping mapping = super.mapping.copy();
        List<Double> mMap = new ArrayList<>(masRecomMap);
        List<Double> fMap = new ArrayList<>(femRecomMap);
        return new GenderSimpleDiploid(dna1, dna2, mapping, mMap, fMap, masculine);
    }

    @Override
    public String toString() {
        String gender = (masculine) ? "M" : "F";
        List<Double> map = (masculine) ? masRecomMap : femRecomMap;
        return "Gender: " + gender +
                ", " + super.toString() +
                ", Recombination probability: " + map.toString();
    }
}
