package ga.operations.selectors;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosome.Chromosome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by david on 3/09/16.
 */
public class SimpleSelector<T extends Chromosome> implements Selector<T> {

    private List<Individual<T>> individuals;
    private List<Double> normalized;
    private SelectionScheme scheme;

    public SimpleSelector(@NotNull final SelectionScheme scheme) {
        this.scheme = scheme;
        normalized = new ArrayList<>();
    }

    @Override
    public List<T> select(final int numOfMates) {
        Set<Integer> set = new HashSet<>(numOfMates);
        while (set.size() < numOfMates) {
            // System.out.println(1);
            set.add(scheme.select(normalized));
        }

        List<T> parents = new ArrayList<>(numOfMates);

        // List<T> parents = set.stream().map(i -> individuals.get(i).getChromosome()).collect(Collectors.toList());
        for (int i : set) {
            parents.add(individuals.get(i).getChromosome());
        }
        return parents;
    }

    @Override
    public void setSelectionData(@NotNull final List<Individual<T>> individuals) {
        final int size = individuals.size();
        this.individuals = individuals;
        normalized.clear();
        double sum = individuals.stream().mapToDouble(Individual::getFitness).sum();
        for (int i = 0; i < size; i++) {
            normalized.add(individuals.get(i).getFitness()/sum);
        }
    }
}
