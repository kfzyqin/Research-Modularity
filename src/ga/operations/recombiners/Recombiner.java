package ga.operations.recombiners;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;

import java.util.List;

/**
 * This interface provides an abstraction for the recombination operator.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public interface Recombiner<T extends Chromosome> {
    /**
     * Performs recombination given parents
     * @param mates parents for recombination
     * @return children reproduced by parents
     */
    List<T> recombine(@NotNull final List<T> mates);
}
