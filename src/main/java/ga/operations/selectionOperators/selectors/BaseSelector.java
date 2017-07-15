package ga.operations.selectionOperators.selectors;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.operations.selectionOperators.selectionSchemes.SelectionScheme;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class provides a simple framework for implementation of selector.
 * The given reference of list of individuals is stored for efficient retrieval purpose.
 * Any subclass of this class must implement a way that facilitates execution according to particular selection scheme.
 *
 * @author Siu Kei Muk (David) and Zhenyue Qin
 * @since 3/09/16.
 */
public abstract class BaseSelector<C extends Chromosome> implements Selector<C> {

    protected List<Individual<C>> individuals;
    protected List<Double> fitnessValues;
    protected SelectionScheme scheme;

    public BaseSelector(@NotNull final SelectionScheme scheme) {
        this.scheme = scheme;
        fitnessValues = new ArrayList<>();
    }

    /**
     * @param numOfMates number of parents of one reproducers (production of offspring)
     * @return the numberOfMates of parents, which are a set of indices of individuals
     */
    @Override
    public List<C> select(final int numOfMates) {
        Set<Integer> set = new HashSet<>(numOfMates);
        while (set.size() < numOfMates) {
            set.add(scheme.select(fitnessValues));
        }

        List<C> parents = new ArrayList<>(numOfMates);

        for (int i : set) {
            parents.add(individuals.get(i).getChromosome());
        }
        return parents;
    }
}
