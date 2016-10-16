package ga.operations.selectionOperators.selectors;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;

import java.util.List;

/**
 * This class implements a simple selector that uses tournament selection scheme.
 *
 * @author Siu Kei Muk (David)
 * @since 11/09/16.
 */
public class SimpleTournamentSelector<C extends Chromosome> extends BaseSelector<C> {

    public SimpleTournamentSelector(final int size, final double dominanceProbability) {
        super(new SimpleTournamentScheme(size, dominanceProbability));
    }

    @Override
    public void setSelectionData(@NotNull List<Individual<C>> individuals) {
        this.individuals = individuals;
        fitnessValues.clear();
        for (int i = 0; i < individuals.size(); i++) {
            fitnessValues.add(individuals.get(i).getFitness());
        }
    }
}
