package ga.components.chromosome;

import com.sun.istack.internal.NotNull;
import ga.components.materials.GeneticMaterial;
import ga.operations.dominanceMappings.DominanceMapping;
import org.apache.commons.collections4.list.FixedSizeList;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a chromosome with genotype that comprises two strands of genetic materials.
 * It requires a dominance mapping for genotype-to-phenotype mapping.
 * The length of each GeneticMaterial must agree. The mapping can be changed during runtime.
 *
 * @author Siu Kei Muk (David)
 * @since 2/09/16.
 */
public abstract class Diploid<M extends GeneticMaterial, G extends GeneticMaterial> extends Chromosome<M,G>{

    /**
     * Constructs a diploid object.
     *
     * @param strand1 first strand of genetic material
     * @param strand2 second strand of genetic material
     * @param mapping genotype-to-phenotype mapping
     */
    public Diploid(@NotNull final M strand1,
                   @NotNull final M strand2,
                   @NotNull final DominanceMapping<M,G> mapping) {
        super(2, strand1.getSize(), mapping);
        if (strand2.getSize() != length)
            throw new IllegalArgumentException("Size of strands does not agree.");
        List<M> strands = new ArrayList<>(2);
        strands.add(strand1);
        strands.add(strand2);
        materials = FixedSizeList.fixedSizeList(strands);
        // this.mapping = mapping;
    }

    /*
    @Override
    public G getPhenotype(final boolean recompute) {
        if (phenotype == null || recompute)
            phenotype = mapping.map(materials);
        return phenotype;
    }*/
}
