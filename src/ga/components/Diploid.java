package ga.components;

import com.sun.istack.internal.NotNull;
import ga.operations.DominanceMapping;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by david on 26/08/16.
 */
public class Diploid extends Chromosome{

    protected DominanceMapping mapping;

    public Diploid(@NotNull final DNAStrand dna1,
                   @NotNull final DNAStrand dna2,
                   @NotNull final DominanceMapping mapping) {
        super(2, dna1.getLength());
        if (length != dna2.getLength())
            throw new IllegalArgumentException("Length of chromosomes do not agree");
        materials[0] = dna1;
        materials[1] = dna2;
        this.mapping = mapping;
    }

    public DNAStrand getPhenotype(){

        DNAStrand dna1 = materials[0];
        DNAStrand dna2 = materials[1];
        Gene[] strand = new Gene[length];
        List<Gene> genes = new LinkedList<>();

        for (int i = 0; i < length; i++) {
            genes.add(dna1.getGene(i));
            genes.add(dna2.getGene(i));
            strand[i] = mapping.map(genes);
            genes.clear();
        }
        return new DNAStrand(strand);
    }

    @Override
    public Chromosome copy() {
        return new Diploid(materials[0].copy(), materials[1].copy(), mapping.copy());
    }
}
