package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.collections.Statistics;
import ga.components.chromosome.Chromosome;
import ga.operations.fitness.Fitness;
import ga.operations.mutators.Mutator;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.recombiners.Recombiner;
import ga.operations.selectors.Selector;

import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public class GAState<T extends Chromosome> {

    private int generation = 0;
    private int numOfMates;
    private final Population<T> population;
    private Fitness fitness;
    private Mutator mutator;
    private Recombiner<T> recombiner;
    private Selector<T> selector;

    public GAState(@NotNull final Population<T> population,
                   @NotNull final Fitness fitness,
                   @NotNull final Mutator mutator,
                   @NotNull final Recombiner<T> recombiner,
                   @NotNull final Selector<T> selector,
                   final int numOfMates) {
        this.population = population;
        this.fitness = fitness;
        this.mutator = mutator;
        this.recombiner = recombiner;
        this.selector = selector;
        this.numOfMates = numOfMates;
        evaluate();
    }

    public void evaluate(){
        population.evaluate(fitness);
    }

    public void record(Statistics<T> statistics) {
        statistics.record(population.getIndividualsView());
    }

    public void preOperate(PriorOperator<T> priorOperator){
        priorOperator.preOperate(population);
    }

    public void recombine(){
        population.setPriorPoolMode(false);
        selector.setSelectionData(population.getIndividualsView());
        while (!population.isReady()) {
            List<T> mates = selector.select(numOfMates);
            List<T> children = recombiner.recombine(mates);
            population.addChildrenChromosome(children);
        }
    }

    public boolean nextGeneration(){
        generation++;
        return population.nextGeneration();
    }

    public void mutate(){
        mutator.mutate(population.getSecondPoolView());
    }

    public Population<T> getPopulation(){
        return population;
    }

    public int getGeneration() {
        return generation;
    }

    public Fitness getFitness() {
        return fitness;
    }

    public void setFitness(@NotNull final Fitness fitness) {
        this.fitness = fitness;
    }

    public void setMutator(@NotNull final Mutator mutator) {
        this.mutator = mutator;
    }

    public void setRecombiner(@NotNull final Recombiner<T> recombiner) {
        this.recombiner = recombiner;
    }
}