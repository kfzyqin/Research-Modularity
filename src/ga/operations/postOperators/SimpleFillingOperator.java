package ga.operations.postOperators;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosome.Chromosome;
import ga.operations.selectors.SelectionScheme;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by david on 9/10/16.
 */
public class SimpleFillingOperator<T extends Chromosome> implements PostOperator<T> {

    private SelectionScheme scheme;

    public SimpleFillingOperator(@NotNull final SelectionScheme scheme) {
        this.scheme = scheme;
    }

    @Override
    public void postOperate(@NotNull final Population<T> population) {
        int size = population.getSize();
        List<Double> fitnessValues = new ArrayList<>(size);
        List<Individual<T>> individuals = population.getIndividualsView();
        for (int i = 0; i < size; i++) fitnessValues.add(individuals.get(i).getFitness());
        // Collections.sort(fitnessValues);
        Set<Integer> survivedIndices = population.getSurvivedIndicesView();
        Set<Integer> selected = new HashSet<>();
        while (!population.isReady()) {
            final int index = scheme.select(fitnessValues);
            if (survivedIndices.contains(index) || selected.contains(index)) continue;
            population.addCandidate(individuals.get(index).copy());
            selected.add(index);
        }
    }
}
