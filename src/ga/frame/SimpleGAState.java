package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.components.chromosome.Chromosome;
import ga.operations.fitnessfunction.FitnessFunction;
import ga.operations.mutation.MutationOperator;
import ga.operations.recombination.Recombiner;
import ga.operations.selectors.Selector;

import java.util.List;

/**
 * This class is a simple implementation of the state. The recombination rate determines the proportion of
 * individuals in the new generation that are produced by recombination.
 *
 * @author Siu Kei Muk (David)
 * @since 12/09/16.
 */
public class SimpleGAState<T extends Chromosome> extends GAState<T> {

    private double recombinationRate;

    public SimpleGAState(@NotNull final Population<T> population,
                         @NotNull final FitnessFunction fitnessFunction,
                         @NotNull final MutationOperator mutationOperator,
                         @NotNull final Recombiner<T> recombiner,
                         @NotNull final Selector<T> selector,
                         final int numOfMates,
                         final double recombinationRate) {
        super(population, fitnessFunction, mutationOperator, recombiner, selector, numOfMates);
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
            population.addCandidateChromosomes(children);
            count += children.size();
        }
    }

    @Override
    public void mutate() {
        mutationOperator.mutate(population.getOffspringPoolView());
    }

    public double getRecombinationRate() {
        return recombinationRate;
    }

    public void setRecombinationRate(final double recombinationRate) {
        filter(recombinationRate);
        this.recombinationRate = recombinationRate;
    }
}
