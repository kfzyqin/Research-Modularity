package ga.components.materials;

import ga.components.genes.Gene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SimpleMaterial is an implementation where the genes are stored in a List.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public class SimpleMaterial implements Material {

    private final Gene[] strand;
    private final int size;

    /**
     * Constructs a SimpleMaterial by a list of genes.
     * @param strand a list of genes
     */
    public SimpleMaterial(List<? extends Gene> strand) {
        size = strand.size();
        this.strand = new Gene[size];
        for (int i = 0; i < size; i++) this.strand[i] = strand.get(i);
        // List<Gene> contents = new ArrayList<>(strand);
        // this.strand = FixedSizeList.fixedSizeList(contents);
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public Gene getGene(final int index){
        return strand[index];
    }

    /**
     * Constructs a deep copy of SimpleMaterial
     * @return a deep copy of the current SimpleMaterial
     */
    @Override
    public SimpleMaterial copy() {
        List<Gene> strand = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            strand.add((Gene)this.strand[i].copy());
        return new SimpleMaterial(strand);
    }

    /**
     * @return String representation of the list of genes.
     */
    @Override
    public String toString() {
        return Arrays.toString(strand);
    }
}
