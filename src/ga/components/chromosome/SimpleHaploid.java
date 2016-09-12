package ga.components.chromosome;

import ga.components.materials.SimpleDNA;
import ga.operations.dominanceMappings.DominanceMapping;
import ga.operations.dominanceMappings.ProjectionMapping;

/**
 * This is a simple implementation of Haploid. The genotype is the same as the phenotype.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public class SimpleHaploid extends Haploid<SimpleDNA, SimpleDNA> {

    private static final DominanceMapping<SimpleDNA,SimpleDNA> identityMapping = new ProjectionMapping<>(0);

    /**
     * Constructs a simple haploid. The mapping is the identity map.
     * @param simpleDna a single strand of DNA
     */
    public SimpleHaploid(SimpleDNA simpleDna) {
        super(simpleDna, identityMapping);
    }

    @Override
    public SimpleDNA getPhenotype(final boolean recompute) {
        return materials.get(0);
    }

    @Override
    public SimpleHaploid copy() {
        return new SimpleHaploid(materials.get(0).copy());
    }

    @Override
    public String toString() {
        return "Genotype/Phenotype: " + materials.get(0).toString();
    }
}
