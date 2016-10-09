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
 * This class is an implementation of post-operator that fills up the remaining space by
 * selecting individuals from the current generation to survive in the next generation
 * according to a given selection scheme.
 *
 * @author Siu Kei Muk (David)
 * @since 12/09/16.
 */
public class SimpleFillingOperatorForNormalizable<T extends Chromosome> implements PostOperator<T> {

    private SelectionScheme scheme;

    public SimpleFillingOperatorForNormalizable(@NotNull final SelectionScheme scheme) {
        this.scheme = scheme;
    }

    @Override
    public void postOperate(@NotNull final Population<T> population) {
        Set<Integer> survivedIndices = population.getSurvivedIndicesView();
        Set<Integer> selected = new HashSet<>();
        List<Individual<T>> individuals = population.getIndividualsView();
        List<Double> normalized = normalizeFitness(individuals);
        final int amount = population.getSize() - population.getNextGenSize();
        while (selected.size() < amount) {
            final int index = scheme.select(normalized);
            if (!survivedIndices.contains(index)) selected.add(index);
        }
        for (Integer i : selected) population.addCandidate(individuals.get(i).copy());
    }

    private List<Double> normalizeFitness(List<Individual<T>> individuals) {
        final double sum = individuals.stream().mapToDouble(Individual::getFitness).sum();
        final int size = individuals.size();
        List<Double> normalized = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            normalized.add(individuals.get(i).getFitness()/sum);
        }
        return normalized;
    }
}
