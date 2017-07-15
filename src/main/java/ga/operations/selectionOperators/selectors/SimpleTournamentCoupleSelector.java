package ga.operations.selectionOperators.selectors;

import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.Coupleable;
import ga.operations.selectionOperators.selectionSchemes.SelectionScheme;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;

/**
 * This class implements tournament selectors for gender reproduction.
 *
 * Created by Zhenyue Qin (秦震岳) on 19/6/17.
 * The Australian National University.
 */
public class SimpleTournamentCoupleSelector<G extends Chromosome & Coupleable> extends CoupleSelector<G> {

    public SimpleTournamentCoupleSelector(final int size) {
        super(new SimpleTournamentScheme(size));
    }
}
