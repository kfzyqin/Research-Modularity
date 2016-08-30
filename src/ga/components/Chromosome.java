package ga.components;

import ga.operations.Mutator;
import ga.others.Copyable;

/**
 * Created by david on 26/08/16.
 */
public abstract class Chromosome implements Copyable<Chromosome> {

    protected final int strands;
    protected final int length;
    protected GeneticMaterial[] materials;
    // protected DNAStrand[] materials;

    public Chromosome(int strands, int length){
        this.strands = strands;
        this.length = length;
        materials = new GeneticMaterial[strands];
        // materials = new DNAStrand[strands];
    }

    public abstract void mutate(Mutator mutator);

    public abstract GeneticMaterial getPhenotype();

    public int getStrands() {
        return strands;
    }

    public int getLength(){
        return length;
    }

    public GeneticMaterial[] getMaterials(){
        return materials;
    }
    /*
    public DNAStrand[] getMaterials() {
        return materials;
    }*/

    /*
    public void setMaterials(DNAStrand[] materials) {
        if (materials.length != strands)
            throw new IllegalArgumentException("Ploidy does not agree");
        for (int i = 0; i < strands; i++){
            if (materials[i].getLength() != length)
                throw new IllegalArgumentException("DNA strand lengths do not agree");
        }
        this.materials = Arrays.copyOf(materials, strands);
    }*/
}
