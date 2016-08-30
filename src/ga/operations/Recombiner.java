package ga.operations;

import com.sun.istack.internal.NotNull;
import ga.components.Chromosome;

import java.util.List;

/**
 * Created by david on 26/08/16.
 */
public interface Recombiner<T extends Chromosome> {
    List<T> recombine(@NotNull final List<T> mates);
}
