package ga.operations;

import com.sun.istack.internal.NotNull;
import ga.components.genes.Gene;
import ga.others.Copyable;

import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public interface DominanceMapping extends Copyable<DominanceMapping> {
    Gene map(@NotNull final List<Gene> genes);
}
