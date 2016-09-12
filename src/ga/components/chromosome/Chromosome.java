package ga.components.chromosome;

import com.sun.istack.internal.NotNull;
import ga.components.materials.GeneticMaterial;
import ga.operations.dominanceMappings.DominanceMapping;
import ga.others.Copyable;

import java.util.Collections;
import java.util.List;

/**
 * This class represents a general chromosome that encodes a candidate solution of an optimization problem.
 * A chromosome comprises a genotype, which consists of a number of genetic materials.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 * @param <M> class/type of genetic material as the genotype
 * @param <G> class/type of genetic material as the phenotype
 */
public abstract class Chromosome<M extends GeneticMaterial, G extends GeneticMaterial> implements Copyable<Chromosome> {

    protected final int strands;
    protected final int length;
    protected List<M> materials;
    protected DominanceMapping<M,G> mapping;
    protected G phenotype = null;

    /**
     * Constructs a chromosome. Note that the 'materials' field is not initialized.
     * It is recommended to use a FixedSizedList from Apache Commons Collections Library.
     *
     * @param strands ploidy of the chromosome
     * @param length number of genes of each haploid in the genotype
     */
    public Chromosome(final int strands, final int length,
                      @NotNull DominanceMapping<M,G> mapping) {
        this.strands = strands;
        this.length = length;
        this.mapping = mapping;
    }

    /**
     *
     * @param recompute determines whether to force remapping of phenotype from genotype
     * @return phenotype of the chromosome
     */
    public G getPhenotype(final boolean recompute) {
        if (phenotype == null || recompute)
            phenotype = mapping.map(materials);
        return phenotype;
    }

    /**
     * @return ploidy of chromosome
     */
    public int getStrands() {
        return strands;
    }

    /**
     * @return number of genes of each haploid in the genotype
     */
    public int getLength() {
        return length;
    }

    /**
     * @return an unmodifiable list containing the genotypes
     */
    public List<M> getMaterialsView() {
        return Collections.unmodifiableList(materials);
    }

    /**
     * @return current genotype-to-phenotype mapping of the chromosome
     */
    public DominanceMapping<M, G> getMapping() {
        return mapping;
    }

    /**
     * @param mapping genotype-to-phenotype mapping to be set
     */
    public void setMapping(@NotNull final DominanceMapping<M,G> mapping) {
        this.mapping = mapping;
    }
}
