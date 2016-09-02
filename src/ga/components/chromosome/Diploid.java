package ga.components.chromosome;

import com.sun.istack.internal.NotNull;
import ga.components.materials.GeneticMaterial;
import ga.operations.dominanceMappings.DominanceMapping;
import org.apache.commons.collections4.list.FixedSizeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2/09/16.
 */
public abstract class Diploid<M extends GeneticMaterial, G extends GeneticMaterial> extends Chromosome<M,G>{

    protected DominanceMapping<M,G> mapping;
    protected G phenotype = null;

    public Diploid(@NotNull final M strand1,
                   @NotNull final M strand2,
                   @NotNull final DominanceMapping<M,G> mapping) {
        super(2, strand1.getSize());
        if (strand2.getSize() != length)
            throw new IllegalArgumentException("Size of strands does not agree.");
        List<M> strands = new ArrayList<>(2);
        strands.add(strand1);
        strands.add(strand2);
        materials = FixedSizeList.fixedSizeList(strands);
        this.mapping = mapping;
    }

    @Override
    public G getPhenotype() {
        if (phenotype == null)
            phenotype = mapping.map(materials);
        return phenotype;
    }

    public void setMapping(DominanceMapping<M,G> mapping) {
        this.mapping = mapping;
    }
}
