package ga.operations.priorOperators;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;

import java.util.List;

/**
 * Created by david on 11/09/16.
 */
public class SimpleElitismOperator<C extends Chromosome> implements PriorOperator<C>{

    private int numOfElites;

    public SimpleElitismOperator(final int numOfElites) {
        filter(numOfElites);
        this.numOfElites = numOfElites;
    }

    private void filter(final int numOfElites) {
        if (numOfElites < 1)
            throw new IllegalArgumentException("Number of elites must be a positive integer.");
    }

    @Override
    public void preOperate(@NotNull Population<C> population) {
        List<Individual<C>> individuals = population.getIndividualsView();
        for (int i = 0; i < numOfElites; i++) {
            population.addCandidate(individuals.get(i).copy());
            population.markSurvivedIndex(i);
        }
    }
}
