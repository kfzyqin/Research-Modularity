package ga.operations;

import com.sun.istack.internal.NotNull;
import ga.components.Gene;

/**
 * Created by david on 26/08/16.
 */
public interface Mutator {
    void mutate(@NotNull final Gene c);
}
