package ga.operations.recombiners;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;

import java.util.List;

/**
 * Created by david on 26/08/16.
 */
public interface Recombiner<T extends Chromosome> {
    List<T> recombine(@NotNull final List<T> mates);
}
