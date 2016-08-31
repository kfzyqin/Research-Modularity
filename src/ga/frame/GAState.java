package ga.frame;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.collections.Statistics;
import ga.components.Chromosome;
import ga.operations.*;

import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public class GAState<T extends Chromosome> {

    private int generation = 0;
    private final Population<T> population;
    private Fitness fitness;
    private Mutator mutator;
    private Recombiner<T> recombiner;
    private Selector selector;

    public GAState(@NotNull final Population<T> population,
                   @NotNull final Fitness fitness,
                   @NotNull final Mutator mutator,
                   @NotNull final Recombiner<T> recombiner,
                   @NotNull final Selector selector) {
        this.population = population;
        this.fitness = fitness;
        this.mutator = mutator;
        this.recombiner = recombiner;
        this.selector = selector;
        evaluate();
    }

    public void evaluate(){
        population.evaluate(fitness);
    }

    public void record(Statistics<T> statistics) {
        population.record(statistics);
    }

    public void preOperate(PriorOperator<T> priorOperator){
        priorOperator.preOperate(population);
    }

    public void recombine(){
        population.setPriorPoolMode(false);
        while (!population.isReady()) {
            List<T> mates = population.selectMates(selector);
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

    public Population<T> getPopulationCopy(){
        return population.copy();
    }

    public int getGeneration() {
        return generation;
    }

    public void setFitness(@NotNull final Fitness fitness) {
        this.fitness = fitness;
    }

    public void setMutator(@NotNull final Mutator mutator) {
        this.mutator = mutator;
    }

    public void setRecombiner(@NotNull final Recombiner recombiner) {
        this.recombiner = recombiner;
    }
}