package ga.operations.selectors;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosome.Chromosome;

import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public interface Selector<T extends Chromosome> {
    List<T> select(final int numOfMates);
    void setSelectionData(List<Individual<T>> individuals);
}
