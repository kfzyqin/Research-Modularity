package ga.components;

import ga.others.Copyable;

import java.util.Arrays;

/**
 * Created by david on 26/08/16.
 */
public class DNAStrand implements GeneticMaterial {

    private final Gene[] strand;

    public DNAStrand(Gene[] strand) {
        this.strand = strand;
    }

    @Override
    public int getSize(){
        return strand.length;
    }

    @Override
    public Gene getGene(final int index){
        return strand[index];
    }

    @Override
    public DNAStrand copy() {
        final int length = this.strand.length;
        Gene[] strand = new Gene[length];
        for (int i = 0; i < length; i++){
            strand[i] = this.strand[i].copy();
        }
        return new DNAStrand(strand);
    }

    @Override
    public String toString() {
        return Arrays.toString(strand);
    }
}
