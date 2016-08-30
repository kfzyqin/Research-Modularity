package ga.operations;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.components.Chromosome;

import java.util.List;

/**
 * Created by david on 28/08/16.
 */
public interface PriorOperation<T extends Chromosome> {
    List<T> preOperate(@NotNull final Population<T> population);
}
