package ga.components.materials;

import ga.components.genes.Gene;
import org.apache.commons.collections4.list.FixedSizeList;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleDNA is an implementation where the genes are stored in a List.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public class SimpleDNA implements GeneticMaterial {

    private final List<Gene> strand;
    private final int size;

    /**
     * Constructs a SimpleDNA by a list of genes.
     * @param strand a list of genes
     */
    public SimpleDNA(List<? extends Gene> strand) {
        size = strand.size();
        List<Gene> contents = new ArrayList<>(strand);
        this.strand = FixedSizeList.fixedSizeList(contents);
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public Gene getGene(final int index){
        return strand.get(index);
    }

    /**
     * Constructs a deep copy of SimpleDNA
     * @return a deep copy of the current SimpleDNA
     */
    @Override
    public SimpleDNA copy() {
        List<Gene> strand = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            strand.add((Gene)this.strand.get(i).copy());
        return new SimpleDNA(strand);
    }

    /**
     * @return String representation of the list of genes.
     */
    @Override
    public String toString() {
        return strand.toString();
    }
}
