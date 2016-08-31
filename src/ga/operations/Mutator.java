package ga.operations;

import com.sun.istack.internal.NotNull;
import ga.components.Chromosome;
import ga.components.Gene;
import ga.components.Individual;

import java.util.List;

/**
 * Created by david on 26/08/16.
 */
public interface Mutator<T extends Chromosome> {
    void mutate(@NotNull final List<Individual<T>> individuals);
}
