package ga.operations;

import ga.collections.Population;
import ga.components.Chromosome;

/**
 * Created by david on 29/08/16.
 */
public interface Initializer<T extends Chromosome> {
    Population<T> initialize();
}
