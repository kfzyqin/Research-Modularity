package ga.operations;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.components.Chromosome;

import java.util.List;

/**
 * Created by david on 28/08/16.
 */
public interface PriorOperator<T extends Chromosome> {
    void preOperate(@NotNull final Population<T> population);
}
