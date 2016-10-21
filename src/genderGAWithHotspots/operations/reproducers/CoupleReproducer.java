package genderGAWithHotspots.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.operations.reproducers.Reproducer;
import genderGAWithHotspots.components.chromosomes.Coupleable;

import java.util.List;

/**
 * Created by david on 29/09/16.
 */
public interface CoupleReproducer<G extends Chromosome & Coupleable> extends Reproducer<G> {
    @Override
    List<G> reproduce(@NotNull final List<G> mates);
}
