package ga.operations.reproducers;

import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.Coupleable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * This interface is the same as Reproducer except that it forces individuals to be Coupleable.
 *
 * @author Siu Kei Muk (David)
 * @since 29/09/16.
 */
public interface CoupleReproducer<G extends Chromosome & Coupleable> extends Reproducer<G> {
    @Override
    List<G> reproduce(@NotNull final List<G> mates);
}
