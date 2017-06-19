package ga.operations.selectionOperators.selectors;

import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.Coupleable;
import ga.operations.selectionOperators.selectionSchemes.SelectionScheme;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;

/**
 * Created by zhenyueqin on 19/6/17.
 */
public class SimpleTournamentCoupleSelector<G extends Chromosome & Coupleable> extends CoupleSelector<G> {

    public SimpleTournamentCoupleSelector(final int size) {
        super(new SimpleTournamentScheme(size));
    }
}
