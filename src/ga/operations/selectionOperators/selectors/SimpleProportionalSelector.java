package ga.operations.selectionOperators.selectors;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.operations.selectionOperators.selectionSchemes.ProportionalScheme;

import java.util.List;

/**
 * This class implements a simple selector that uses proportionate selection scheme.
 *
 * @author Siu Kei Muk (David)
 * @since 11/09/16
 */
public class SimpleProportionalSelector<C extends Chromosome> extends BaseSelector<C> {

    public SimpleProportionalSelector() {
        super(new ProportionalScheme());
    }

    @Override
    public void setSelectionData(@NotNull final List<Individual<C>> individuals) {
        final int size = individuals.size();
        this.individuals = individuals;
        fitnessValues.clear();
        double sum = 0;
        for (Individual<C> individual : individuals) sum += individual.getFitness();
        for (int i = 0; i < size; i++) {
            fitnessValues.add(individuals.get(i).getFitness() / sum);
        }
    }
}
