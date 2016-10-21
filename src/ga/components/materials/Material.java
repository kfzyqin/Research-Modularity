package ga.components.materials;

import ga.components.genes.Gene;
import ga.others.Copyable;

/**
 * This interface abstracts an indexable genetic material.
 * A genetic material is a container of genes which can be extracted by index from 0 to size-1.
 * This interface extends the Copyable interface.
 *
 * @author Siu Kei Muk (David)
 * @since 30/08/16.
 */
public interface Material extends Copyable<Material> {
    /**
     * @return Number of genes contained in the material.
     */
    int getSize();

    /**
     * @param index location of gene
     * @return gene located at the specified position
     */
    Gene getGene(final int index);
}
