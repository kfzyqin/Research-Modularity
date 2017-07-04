package ga.frame.states;

import ga.collections.Individual;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.CoupleableWithHotspot;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

import java.util.List;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public class SimpleGenderHotspotMultipleTargetState<G extends Chromosome & CoupleableWithHotspot> extends GenderHotspotMultipleTargetState<G> {

    protected double reproductionRate;

    /**
     * Constructs an initial state for the GA
     *
     * @param population           initial population
     * @param fitnessFunction      fitness function
     * @param mutator              mutators operator
     * @param reproducer           reproducers operator
     * @param selector             parents selector
     * @param numOfMates           number of parents per reproduction
     * @param expressionMapMutator
     * @param hotspotMutator
     */
    public SimpleGenderHotspotMultipleTargetState(
            Population<G> population,
            FitnessFunction fitnessFunction,
            Mutator mutator,
            Reproducer<G> reproducer,
            Selector<G> selector,
            final int numOfMates,
            final double reproductionRate,
            ExpressionMapMutator expressionMapMutator,
            HotspotMutator hotspotMutator) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates, expressionMapMutator, hotspotMutator);
        this.reproductionRate = reproductionRate;
    }

    @Override
    public void mutateExpressionMap() {
        if (expressionMapMutator == null) return;
        for (Individual<G> individual : population.getOffspringPoolView())
            expressionMapMutator.mutate(individual.getChromosome().getMapping());
    }

    @Override
    public void reproduce() {
        population.setMode(PopulationMode.REPRODUCE);
        selector.setSelectionData(population.getIndividualsView());
        int count = 0;
        final int size = (int) Math.round(population.getSize()* reproductionRate);
        while (count < size && !population.isReady()) {
            List<G> mates = selector.select(numOfMates);
            List<G> children = reproducer.reproduce(mates);
            population.addCandidateChromosomes(children);
            count += children.size();
        }
    }

    @Override
    public void mutate() {
        mutator.mutate(population.getOffspringPoolView());
    }

    @Override
    public void mutateHotspot() {
        if (hotspotMutator == null) return;
        for (Individual<G> individual : population.getOffspringPoolView())
            hotspotMutator.mutate(individual.getChromosome().getHotspot());
    }

    public double getReproductionRate() {
        return reproductionRate;
    }

    public void setReproductionRate(final double reproductionRate) {
        filter(reproductionRate);
        this.reproductionRate = reproductionRate;
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }
}
