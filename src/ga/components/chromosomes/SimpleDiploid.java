package ga.components.chromosomes;

import com.sun.istack.internal.NotNull;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

/**
 * This class implements a simple diploid that uses SimpleMaterial class as genotype and phenotype.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public class SimpleDiploid extends Diploid<SimpleMaterial, SimpleMaterial>{

    public SimpleDiploid(@NotNull final SimpleMaterial dna1,
                         @NotNull final SimpleMaterial dna2,
                         @NotNull final ExpressionMap<SimpleMaterial, SimpleMaterial> mapping) {
        super(dna1, dna2, mapping);
    }

    @Override
    public SimpleDiploid copy() {
        return new SimpleDiploid(genotype.get(0).copy(), genotype.get(1).copy(), mapping.copy());
    }

    @Override
    public String toString() {
        return "\nDNA_1: " + genotype.get(0).toString() +
                "\nDNA_2: " + genotype.get(1).toString() +
                "\nDominance:\n" + mapping.toString() +
                "\nPhenotype: " + getPhenotype(false).toString();

    }
}
