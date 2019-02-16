package ga.operations.selectionOperators.selectors;

import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.operations.selectionOperators.selectionSchemes.SelectionScheme;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class provides a simple framework for implementation of selector.
 * The given reference of list of individuals is stored for efficient retrieval purpose.
 * Any subclass of this class must implement a way that facilitates execution according to particular selection scheme.
 *
 * @author Siu Kei Muk (David) and Zhenyue Qin
 * @since 3/09/16.
 */
public class RandomSelector<C extends Chromosome> implements Selector<C> {

    protected List<Individual<C>> individuals;
    public RandomSelector() {

    }

    @Override
    public List<C> select(int numOfMates) {
        List<C> parents = new ArrayList<>(numOfMates);
        for (int i=0; i<numOfMates; i++) {
            final int index = ThreadLocalRandom.current().nextInt(this.individuals.size());
            parents.add(individuals.get(index).getChromosome());
        }
        return parents;
    }

    @Override
    public void setSelectionData(List<Individual<C>> individuals) {
        this.individuals = individuals;
    }
}
