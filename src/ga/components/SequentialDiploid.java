package ga.components;

import com.sun.istack.internal.NotNull;
import ga.operations.DominanceMapping;
import ga.operations.Mutator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by david on 26/08/16.
 */
public class SequentialDiploid extends Chromosome{

    protected DominanceMapping mapping;

    public SequentialDiploid(@NotNull final DNAStrand dna1,
                             @NotNull final DNAStrand dna2,
                             @NotNull final DominanceMapping mapping) {
        super(2, dna1.getSize());
        if (length != dna2.getSize())
            throw new IllegalArgumentException("Length of chromosomes do not agree");
        materials = new DNAStrand[length];
        materials[0] = dna1;
        materials[1] = dna2;
        this.mapping = mapping;
    }

    @Override
    public void mutate(Mutator mutator) {
        DNAStrand dna1 = (DNAStrand) materials[0];
        DNAStrand dna2 = (DNAStrand) materials[1];
        for (int i = 0; i < length; i++) {
            mutator.mutate(dna1.getGene(i));
            mutator.mutate(dna2.getGene(i));
        }
    }

    @Override
    public DNAStrand getPhenotype(){

        DNAStrand dna1 = (DNAStrand) materials[0];
        DNAStrand dna2 = (DNAStrand) materials[1];
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
        return new SequentialDiploid((DNAStrand)materials[0].copy(), (DNAStrand)materials[1].copy(), mapping.copy());
    }

    @Override
    public String toString() {
        return "DNA_1: " + materials[0].toString() +
                ", DNA_2: " + materials[1].toString() +
                ", Dominance: " + mapping.toString() +
                ", Phenotype: " + getPhenotype().toString();

    }
}
