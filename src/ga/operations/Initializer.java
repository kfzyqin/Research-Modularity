package ga.operations;

import ga.collections.Population;
import ga.components.chromosome.Chromosome;

/**
 * Created by david on 29/08/16.
 */
public interface Initializer<T extends Chromosome> {
    int getSize();
    void setSize(final int size);
    Population<T> initialize();
}
