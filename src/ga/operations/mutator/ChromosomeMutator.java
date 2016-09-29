package ga.operations.mutator;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosome.Chromosome;

import java.util.List;

/**
 * This interface provides an abstraction for mutator operation.
 * An implementation strategy is to have a 3-layer nested loop (individual-layer, chromosome-layer, gene-layer) to loop
 * through every gene, and use a gene-value generator to do the random draw.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public interface ChromosomeMutator<T extends Chromosome> {
    /**
     * Mutates the given list of individuals.
     * @param individuals individuals to be mutated
     */
    void mutate(@NotNull final List<Individual<T>> individuals);
}
