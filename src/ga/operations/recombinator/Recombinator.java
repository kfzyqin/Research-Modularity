package ga.operations.recombinator;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;

import java.util.List;

/**
 * This interface provides an abstraction for the recombinator operator.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public interface Recombinator<T extends Chromosome> {
    /**
     * Performs recombinator given parents
     * @param mates parents for recombinator
     * @return children reproduced by parents
     */
    List<T> recombine(@NotNull final List<T> mates);
}
