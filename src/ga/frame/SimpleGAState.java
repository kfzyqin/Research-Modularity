package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.components.chromosome.Chromosome;
import ga.operations.fitness.Fitness;
import ga.operations.mutators.Mutator;
import ga.operations.recombiners.Recombiner;
import ga.operations.selectors.Selector;

import java.util.List;

/**
 * Created by david on 12/09/16.
 */
public class SimpleGAState<T extends Chromosome> extends GAState<T> {

    private double recombinationRate;

    public SimpleGAState(@NotNull final Population<T> population,
                         @NotNull final Fitness fitness,
                         @NotNull final Mutator mutator,
                         @NotNull final Recombiner<T> recombiner,
                         @NotNull final Selector<T> selector,
                         final int numOfMates,
                         final double recombinationRate) {
        super(population, fitness, mutator, recombiner, selector, numOfMates);
        setRecombinationRate(recombinationRate);
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    @Override
    public void recombine() {
        population.setMode(PopulationMode.RECOMBINE);
        selector.setSelectionData(population.getIndividualsView());
        int count = 0;
        final int size = (int) Math.round(population.getSize()*recombinationRate);
        while (count < size && !population.isReady()) {
            List<T> mates = selector.select(numOfMates);
            List<T> children = recombiner.recombine(mates);
            population.addChildrenChromosome(children);
            count += children.size();
        }
    }

    @Override
    public void mutate() {
        mutator.mutate(population.getOffspringPoolView());
    }

    public double getRecombinationRate() {
        return recombinationRate;
    }

    public void setRecombinationRate(final double recombinationRate) {
        filter(recombinationRate);
        this.recombinationRate = recombinationRate;
    }
}
