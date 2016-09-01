package ga.components.materials;

import ga.components.genes.Gene;
import org.apache.commons.collections4.list.FixedSizeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 26/08/16.
 */
public class DNAStrand<V> implements GeneticMaterial {

    private final List<Gene<V>> strand;
    private final int size;

    public DNAStrand(List<? extends Gene<V>> strand) {
        size = strand.size();
        List<Gene<V>> contents = new ArrayList<>(strand);
        this.strand = FixedSizeList.fixedSizeList(contents);
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public Gene<V> getGene(final int index){
        return strand.get(index);
    }

    @Override
    public DNAStrand<V> copy() {
        List<Gene<V>> strand = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            strand.add(this.strand.get(i).copy());
        return new DNAStrand<>(strand);
    }

    @Override
    public String toString() {
        return strand.toString();
    }
}
