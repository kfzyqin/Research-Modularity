package ga.components.materials;

import ga.components.genes.Gene;
import ga.others.Copyable;

/**
 * Created by david on 30/08/16.
 */
public interface GeneticMaterial extends Copyable<GeneticMaterial> {
    int getSize();
    Gene getGene(final int index);
}
