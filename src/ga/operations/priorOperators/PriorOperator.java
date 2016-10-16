package ga.operations.priorOperators;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;

/**
 *
 *
 * Created by david on 28/08/16.
 */
public interface PriorOperator<C extends Chromosome> {
    void preOperate(@NotNull final Population<C> population);
}
