package ga.components;

import ga.others.Copyable;

/**
 * Created by david on 26/08/16.
 */
public class DNAStrand implements Copyable<DNAStrand> {

    private final Gene[] strand;

    public DNAStrand(Gene[] strand) {
        this.strand = strand;
    }

    public int getLength(){
        return strand.length;
    }

    public Gene getGene(int index){
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
}
