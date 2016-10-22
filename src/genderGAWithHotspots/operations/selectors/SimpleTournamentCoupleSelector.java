package genderGAWithHotspots.operations.selectors;

import ga.components.chromosomes.Chromosome;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import genderGAWithHotspots.components.chromosomes.Coupleable;

/**
 * Created by david on 29/09/16.
 */
public class SimpleTournamentCoupleSelector<G extends Chromosome & Coupleable> extends CoupleSelector<G>{

    public SimpleTournamentCoupleSelector(final int size, final double dominanceProbability) {
        super(new SimpleTournamentScheme(size, dominanceProbability));
    }
}
