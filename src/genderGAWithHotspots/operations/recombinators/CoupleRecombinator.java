package genderGAWithHotspots.operations.recombinators;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.operations.recombinators.Recombinator;
import genderGAWithHotspots.components.chromosomes.Coupleable;

import java.util.List;

/**
 * Created by david on 29/09/16.
 */
public interface CoupleRecombinator<G extends Chromosome & Coupleable> extends Recombinator<G> {
    @Override
    List<G> recombine(@NotNull final List<G> mates);
}
