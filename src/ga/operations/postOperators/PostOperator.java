package ga.operations.postOperators;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.components.chromosome.Chromosome;

/**
 * Created by david on 12/09/16.
 */
public interface PostOperator<T extends Chromosome> {
    void postOperate(@NotNull final Population<T> population);
}
