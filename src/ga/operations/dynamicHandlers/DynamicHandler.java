package ga.operations.dynamicHandlers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.frame.State;

/**
 * This interface provides an abstraction of handler that handles changes occurred in the fitness function/environment.
 * Usually, it is used to re-evaluate the fitness function values and perform dominance change after a change is occurred.
 *
 * @author Siu Kei Muk (David)
 * @since 3/09/16.
 */
public interface DynamicHandler<C extends Chromosome> {
    /**
     * Handles the change in environment.
     *
     * @param state current state of genetic algorithm
     * @return false: not handled, true: handled
     */
    boolean handle(@NotNull final State<C> state);
}
