package ga.operations.dynamicHandler;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.frame.GAState;

/**
 * This interface provides an abstraction of handler that handles changes occurred in the fitnessfunction function/environment.
 * Usually, it is used to re-evaluate the fitnessfunction function values and perform dominance change after a change is occurred.
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
    boolean handle(@NotNull final GAState<C> state);
}
