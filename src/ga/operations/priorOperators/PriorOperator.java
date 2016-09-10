package ga.operations.priorOperators;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.components.chromosome.Chromosome;

/**
 * Created by david on 28/08/16.
 */
public interface PriorOperator<T extends Chromosome> {
    void preOperate(@NotNull final Population<T> population);
}
