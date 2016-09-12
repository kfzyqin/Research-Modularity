package ga.operations.selectors;

import ga.collections.Individual;
import ga.components.chromosome.Chromosome;

import java.util.List;

/**
 * This interface abstracts the selection of parents from a given list of individuals.
 * The given list of individuals is assumed to be sorted in descending order according to fitness function value.
 * Before a selection can be performed, the client code must provide the sorted individuals of the current generation
 * for efficiency reason. The selection can be done by calling the 'select' method.
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public interface Selector<T extends Chromosome> {
    /**
     * @param numOfMates number of parents of one recombination (production of offspring)
     * @return parents for recombination
     */
    List<T> select(final int numOfMates);

    /**
     * @param individuals list of individuals of the current generation sorted in descending order
     */
    void setSelectionData(List<Individual<T>> individuals);
}
