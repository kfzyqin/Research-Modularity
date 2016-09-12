package ga.components.chromosome;

import com.sun.istack.internal.NotNull;
import ga.components.materials.SimpleDNA;
import ga.operations.dominanceMappings.DominanceMapping;

/**
 * This class implements a simple diploid that uses SimpleDNA class as genotype and phenotype.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public class SimpleDiploid extends Diploid<SimpleDNA, SimpleDNA>{

    public SimpleDiploid(@NotNull final SimpleDNA dna1,
                         @NotNull final SimpleDNA dna2,
                         @NotNull final DominanceMapping<SimpleDNA, SimpleDNA> mapping) {
        super(dna1, dna2, mapping);
    }

    @Override
    public SimpleDiploid copy() {
        return new SimpleDiploid(materials.get(0).copy(), materials.get(1).copy(), mapping.copy());
    }

    @Override
    public String toString() {
        return "\nDNA_1: " + materials.get(0).toString() +
                "\nDNA_2: " + materials.get(1).toString() +
                "\nDominance:\n" + mapping.toString() +
                "\nPhenotype: " + getPhenotype(false).toString();

    }
}
