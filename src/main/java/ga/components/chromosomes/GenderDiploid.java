package ga.components.chromosomes;

import com.sun.istack.internal.NotNull;
import ga.components.hotspots.Hotspot;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.List;

/**
 * Created by zhenyueqin on 15/6/17.
 */
public class GenderDiploid extends SimpleDiploid implements Coupleable {

    protected final boolean masculine;

    public GenderDiploid(@NotNull final SimpleMaterial dna1,
                         @NotNull final SimpleMaterial dna2,
                         @NotNull final ExpressionMap<SimpleMaterial, SimpleMaterial> mapping,
                         final boolean masculine) {
        super(dna1, dna2, mapping);
        this.masculine = masculine;
    }

    @Override
    public boolean isMasculine() {
        return masculine;
    }

    @Override
    public GenderDiploid copy() {
        SimpleMaterial dna1 = genotype.get(0).copy();
        SimpleMaterial dna2 = genotype.get(1).copy();
        ExpressionMap mapping = super.mapping.copy();
        return new GenderDiploid(dna1, dna2, mapping, masculine);
    }

    @Override
    public String toString() {
        String gender = (masculine) ? "M" : "F";
        return "Gender: " + gender +
                ", " + super.toString();
    }
}
