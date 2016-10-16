package ga.components.chromosomes;

import com.sun.istack.internal.NotNull;
import ga.components.materials.GeneticMaterial;
import ga.operations.dominanceMap.DominanceMap;
import org.apache.commons.collections4.list.FixedSizeList;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a chromosomes with genotype consisting of exactly one single strand of genetic material.
 * The phenotype is usually the same as the genotype, but not always the case.
 *
 * @author Siu Kei Muk (David)
 * @since 2/09/16.
 */
public abstract class Haploid<M extends GeneticMaterial, P extends GeneticMaterial> extends Chromosome<M, P> {

    /**
     * Constructs a Haploid object.
     * @param strand one single strand of genetic material
     * @param mapping genotype-to-phenotype mapping
     */
    public Haploid(@NotNull final M strand, @NotNull DominanceMap<M, P> mapping) {
        super(1, strand.getSize(), mapping);
        List<M> strands = new ArrayList<>(1);
        strands.add(strand);
        materials = FixedSizeList.fixedSizeList(strands);
    }

    @Override
    public String toString() {
        return "Genotype: " + materials.get(0).toString();
    }
}
