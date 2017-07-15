package ga.components.chromosomes;

import com.sun.istack.internal.NotNull;
import ga.components.materials.Material;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.ArrayList;

/**
 * This class represents a chromosomes with genotype that comprises two strands of genetic genotype.
 * It requires a dominance mapping for genotype-to-phenotype mapping.
 * The length of each Material must agree. The mapping can be changed during runtime.
 *
 * @author Siu Kei Muk (David)
 * @since 2/09/16.
 */
public abstract class Diploid<M extends Material, P extends Material> extends Chromosome<M, P>{

    /**
     * Constructs a diploid object.
     *
     * @param strand1 first strand of genetic material
     * @param strand2 second strand of genetic material
     * @param mapping genotype-to-phenotype mapping
     */
    public Diploid(@NotNull final M strand1,
                   @NotNull final M strand2,
                   @NotNull final ExpressionMap<M, P> mapping) {
        super(2, strand1.getSize(), mapping);
        if (strand2.getSize() != length)
            throw new IllegalArgumentException("Size of strands does not agree.");
        genotype = new ArrayList<>(2);
        genotype.add(strand1);
        genotype.add(strand2);
    }
}
