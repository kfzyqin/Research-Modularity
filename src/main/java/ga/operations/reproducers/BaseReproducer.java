package ga.operations.reproducers;

import ga.components.chromosomes.Chromosome;
import ga.components.genes.Gene;
import ga.components.materials.Material;

/**
 * Created by Zhenyue Qin (秦震岳) on 3/7/17.
 * The Australian National University.
 */
public abstract class BaseReproducer <C extends Chromosome> implements Reproducer<C> {
    protected void crossoverTwoDNAsAtPosition(Material dna1, Material dna2, int index) {
        final int i1 = ((Gene<Integer>) dna1.getGene(index)).getValue();
        final int i2 = ((Gene<Integer>) dna2.getGene(index)).getValue();
        dna1.getGene(index).setValue(i2);
        dna2.getGene(index).setValue(i1);
    }
}
