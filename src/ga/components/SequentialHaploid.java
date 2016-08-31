package ga.components;

import ga.operations.Mutator;

/**
 * Created by david on 26/08/16.
 */
public class SequentialHaploid extends Chromosome {

    public SequentialHaploid(DNAStrand dnaStrand) {
        super(1, dnaStrand.getSize());
        materials.set(0, dnaStrand);
    }

    @Override
    public GeneticMaterial getPhenotype() {
        return materials.get(0).copy();
    }

    @Override
    public SequentialHaploid copy() {
        return new SequentialHaploid((DNAStrand) materials.get(0).copy());
    }

    @Override
    public String toString() {
        return "Genotype/Phenotype: " + materials.get(0).toString();
    }
}
