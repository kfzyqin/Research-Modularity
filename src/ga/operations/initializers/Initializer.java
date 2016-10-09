package ga.operations.initializers;

import ga.collections.Population;
import ga.components.chromosome.Chromosome;

/**
 * This interface provides an abstraction for the (random) initializer of population.
 * It is recommended to use a GeneFactory to generate random gene.
 *
 * @author Siu Kei Muk (David)
 * @since 29/08/16.
 */
public interface Initializer<C extends Chromosome> {
    /**
     * Returns the size of population to be initialized.
     * @return size of population
     */
    int getSize();

    /**
     * Sets the size of population to be initialized.
     * @param size size of population
     */
    void setSize(final int size);

    /**
     * (Random) Initialization of the initial population
     * @return Randomly initialized population
     */
    Population<C> initialize();
}
