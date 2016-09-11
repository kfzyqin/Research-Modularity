package ga.operations.mutators;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosome.Chromosome;

import java.util.List;

/**
 * Created by david on 26/08/16.
 */
public interface Mutator<T extends Chromosome> {
    void mutate(@NotNull final List<Individual<T>> individuals);
}
