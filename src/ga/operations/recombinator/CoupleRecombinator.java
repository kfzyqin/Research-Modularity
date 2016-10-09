package ga.operations.recombinator;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;
import ga.components.chromosome.Coupleable;

import java.util.List;

/**
 * Created by david on 29/09/16.
 */
public interface CoupleRecombinator<G extends Chromosome & Coupleable> extends Recombinator<G> {
    @Override
    List<G> recombine(@NotNull final List<G> mates);
}
