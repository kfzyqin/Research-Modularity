package ga.components;

import ga.operations.Mutator;

/**
 * Created by david on 26/08/16.
 */
public class SequentialHaploid extends Chromosome {

    public SequentialHaploid(DNAStrand dnaStrand) {
        super(1, dnaStrand.getSize());
        materials[0] = dnaStrand;
    }

    @Override
    public GeneticMaterial getPhenotype() {
        return materials[0].copy();
    }

    @Override
    public void mutate(Mutator mutator) {
        DNAStrand dna = (DNAStrand) materials[0];
        for (int i = 0; i < length; i++)
            mutator.mutate(dna.getGene(i));
    }

    @Override
    public Chromosome copy() {
        return new SequentialHaploid((DNAStrand) materials[0].copy());
    }

    @Override
    public String toString() {
        return "Genotype/Phenotype: " + materials[0].toString();
    }
}
