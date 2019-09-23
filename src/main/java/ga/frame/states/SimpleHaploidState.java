package ga.frame.states;

import ga.collections.Individual;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.components.chromosomes.Chromosome;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * This class is a simple implementation of the state. The reproducers rate determines the proportion of
 * individuals in the new generation that are produced by reproducers.
 *
 * @author Siu Kei Muk (David)
 * @since 12/09/16.
 */
public class SimpleHaploidState<C extends Chromosome> extends State<C> {

    protected double reproductionRate;

    public SimpleHaploidState(@NotNull final Population<C> population,
                              @NotNull final FitnessFunction fitnessFunction,
                              @NotNull final Mutator mutator,
                              @NotNull final Reproducer<C> reproducer,
                              @NotNull final Selector<C> selector,
                              final int numOfMates,
                              final double reproductionRate) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates);
        setReproductionRate(reproductionRate);
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    @Override
    public void reproduce() {
        population.setMode(PopulationMode.REPRODUCE);
        selector.setSelectionData(population.getIndividualsView());
        int count = 0;
        final int size = (int) Math.round(population.getSize()* reproductionRate);
        while (count < size && !population.isReady()) {
            List<C> mates = selector.select(numOfMates);
            List<C> children = reproducer.reproduce(mates);
            population.addCandidateChromosomes(children);
            count += children.size();
        }
    }

    @Override
    public void mutate() {
        mutator.mutate(population.getOffspringPoolView());
    }

    public double getReproductionRate() {
        return reproductionRate;
    }

    public void setReproductionRate(final double reproductionRate) {
        filter(reproductionRate);
        this.reproductionRate = reproductionRate;
    }

}
