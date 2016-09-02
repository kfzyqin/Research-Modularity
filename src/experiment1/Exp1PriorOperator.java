package experiment1;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.collections.Individual;
import ga.components.chromosome.SequentialHaploid;
import ga.operations.mutators.Mutator;
import ga.operations.PriorOperator;
import ga.operations.selectors.Selector;

import java.util.List;

/**
 * Created by david on 31/08/16.
 */
public class Exp1PriorOperator implements PriorOperator<SequentialHaploid> {

    private int numOfElites;
    private Selector selector;
    private Mutator<SequentialHaploid> mutator = null;

    public Exp1PriorOperator(final int numOfElites, Selector selector) {
        if (numOfElites < 1)
            throw new IllegalArgumentException("Number of elites must be a positive integer.");
        this.numOfElites = numOfElites;
        this.selector = selector;
    }

    @Override
    public void preOperate(@NotNull Population<SequentialHaploid> population) {
        population.setPriorPoolMode(true);
        /*
        List<Integer> indices = new ArrayList<>(numOfElites);
        List<Double> fitnessValues = population.getFitnessValuesView();
        List<Individual<SequentialHaploid>> individuals = population.getIndividualsView();
        while (indices.size() < numOfElites) {
            final int index = selector.select(fitnessValues);
            if (!indices.contains(index)) indices.add(index);
        }
        System.out.println(indices);
        */
        List<Individual<SequentialHaploid>> individuals = population.getIndividualsView();
        for (int i = 0; i < 20; i++) population.addChild(individuals.get(i));
    }

    private void mutate(@NotNull final List<Individual<SequentialHaploid>> mutant,
                        @NotNull final Mutator<SequentialHaploid> mutator) {
        mutator.mutate(mutant);
    }

    public int getNumOfElites() {
        return numOfElites;
    }

    public void setNumOfElites(int numOfElites) {
        this.numOfElites = numOfElites;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public void setMutator(final Mutator<SequentialHaploid> mutator) {
        this.mutator = mutator;
    }
}
