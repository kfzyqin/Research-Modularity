package ga.operations.recombinators;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;

import java.util.List;

/**
 * This interface provides an abstraction for the recombination operator.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public interface Recombinator<C extends Chromosome> {
    /**
     * Performs recombination given parents
     * @param mates parents for recombination
     * @return children reproduced by parents
     */
    List<C> recombine(@NotNull final List<C> mates);
}
