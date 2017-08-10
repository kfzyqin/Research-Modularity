package ga.frame.states;

import ga.collections.Individual;
import ga.collections.Population;
import ga.collections.PopulationMode;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.Coupleable;
import ga.components.chromosomes.CoupleableWithHotspot;
import ga.components.chromosomes.Diploid;
import ga.operations.dominanceMapMutators.ExpressionMapMutator;
import ga.operations.expressionMaps.ExpressionMap;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

import java.util.List;

/**
 * Created by Zhenyue Qin (秦震岳) on 8/8/17.
 * The Australian National University.
 */
public class SimpleGenderDiploidState<G extends Chromosome & Coupleable> extends State<G>
        implements DiploidState<G> {

    protected double reproductionRate;
    protected ExpressionMapMutator expressionMapMutator;

    /**
     * Constructs an initial state for the GA
     *
     * @param population      initial population
     * @param fitnessFunction fitness function
     * @param mutator         mutators operator
     * @param reproducer      reproducers operator
     * @param selector        parents selector
     * @param numOfMates      number of parents per reproduction
     */
    public SimpleGenderDiploidState(Population<G> population,
                                    FitnessFunction fitnessFunction,
                                    Mutator mutator,
                                    Reproducer<G> reproducer,
                                    Selector<G> selector,
                                    int numOfMates,
                                    double reproductionRate,
                                    ExpressionMapMutator expressionMapMutator) {
        super(population, fitnessFunction, mutator, reproducer, selector, numOfMates);
        this.reproductionRate = reproductionRate;
        this.expressionMapMutator = expressionMapMutator;
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
}
