package ga.frame.states;

import ga.collections.Individual;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.WithHotspot;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

import java.util.List;

/**
 * Created by zhenyueqin on 21/6/17.
 */
public class SimpleHotspotDiploidMultipleTargetState<C extends Chromosome & WithHotspot> extends HotspotDiploidMultipleTargetState<C> {
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
     * @param expressionMapMutator expression map mutator
     * @param hotspotMutator       hotspot mutator
     */
    public SimpleHotspotDiploidMultipleTargetState(
            Population<C> population,
            FitnessFunction fitnessFunction,
            Mutator mutator,
            Reproducer<C> reproducer,
            Selector<C> selector,
            int numOfMates,
            final double reproductionRate,
            ExpressionMapMutator expressionMapMutator,
            HotspotMutator hotspotMutator) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates, expressionMapMutator, hotspotMutator);
        this.reproductionRate = reproductionRate;
    }

    @Override
    public void mutateHotspot() {
        if (hotspotMutator == null) return;
        for (Individual<C> individual : population.getOffspringPoolView())
            hotspotMutator.mutate(individual.getChromosome().getHotspot());
    }

    @Override
    public void mutateExpressionMap() {
        if (expressionMapMutator == null) return;
        for (Individual<C> individual : population.getOffspringPoolView())
            expressionMapMutator.mutate(individual.getChromosome().getMapping());
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

}
