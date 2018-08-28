package ga.operations.selectionOperators.selectors;

import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * This class implements a simple selector that uses tournament selection scheme.
 *
 * @author Siu Kei Muk (David) and Zhenyue Qin
 * @since 11/09/16.
 */
public class SimpleTournamentSelector<C extends Chromosome> extends BaseSelector<C> {

    /**
     *
     * @param size tournament size
     */
    public SimpleTournamentSelector(final int size) {
        super(new SimpleTournamentScheme(size));
    }

    /**
     * 
     * @param individuals list of individuals of the current generation sorted in descending order
     */
    @Override
    public void setSelectionData(@NotNull List<Individual<C>> individuals) {
        this.individuals = individuals;
        fitnessValues.clear();
        for (int i = 0; i < individuals.size(); i++) {
            fitnessValues.add(individuals.get(i).getFitness());
        }
    }
}
