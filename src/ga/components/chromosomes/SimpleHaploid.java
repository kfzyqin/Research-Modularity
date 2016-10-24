package ga.components.chromosomes;

import ga.components.materials.SimpleMaterial;
import ga.operations.dominanceMaps.DominanceMap;
import ga.operations.dominanceMaps.ProjectionMap;

/**
 * This is a simple implementation of Haploid. The genotype is the same as the phenotype.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public class SimpleHaploid extends Haploid<SimpleMaterial, SimpleMaterial> {

    private static final DominanceMap<SimpleMaterial,SimpleMaterial> identityMapping = new ProjectionMap<>(0);

    /**
     * Constructs a simple haploid. The mapping is the identity map.
     * @param simpleMaterial a single strand of DNA
     */
    public SimpleHaploid(SimpleMaterial simpleMaterial) {
        super(simpleMaterial, identityMapping);
    }

    @Override
    public SimpleMaterial getPhenotype(final boolean recompute) {
        return genotype.get(0);
    }

    @Override
    public SimpleHaploid copy() {
        return new SimpleHaploid(genotype.get(0).copy());
    }

    @Override
    public String toString() {
        return "Genotype/Phenotype: " + genotype.get(0).toString();
    }
}
