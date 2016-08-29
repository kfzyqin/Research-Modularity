package ga.components;

/**
 * Created by david on 26/08/16.
 */
public class Haploid extends Chromosome {

    public Haploid(DNAStrand dnaStrand) {
        super(1, dnaStrand.getLength());
        materials[0] = dnaStrand;
    }

    @Override
    public DNAStrand getPhenotype() {
        return materials[0].copy();
    }

    @Override
    public Chromosome copy() {
        return new Haploid(materials[0].copy());
    }
}
