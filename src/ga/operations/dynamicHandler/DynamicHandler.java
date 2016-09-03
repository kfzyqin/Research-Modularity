package ga.operations.dynamicHandler;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;
import ga.frame.GAState;

/**
 * Created by david on 3/09/16.
 */
public interface DynamicHandler<T extends Chromosome> {
    boolean handle(@NotNull final GAState<T> state);
}
