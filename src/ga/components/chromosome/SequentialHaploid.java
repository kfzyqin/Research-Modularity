package ga.components.chromosome;

import ga.components.materials.DNAStrand;

/**
 * Created by david on 26/08/16.
 */
public class SequentialHaploid extends Haploid<DNAStrand, DNAStrand> {

    public SequentialHaploid(DNAStrand dnaStrand) {
        super(dnaStrand);
    }

    @Override
    public DNAStrand getPhenotype() {
        return materials.get(0);
    }

    @Override
    public SequentialHaploid copy() {
        return new SequentialHaploid(materials.get(0).copy());
    }

    @Override
    public String toString() {
        return "Genotype/Phenotype: " + materials.get(0).toString();
    }
}
