import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.Map;

/**
 * Created by david on 27/08/16.
 */
public class GAState<T extends Chromosome> {

    private int generation = 0;
    private final Population<T> population;
    private Fitness fitness;
    private Mutator<T> mutator;
    private Recombiner<T> recombiner;
    private Selector selector;

    public GAState(@NotNull final Population<T> population,
                   @NotNull final Fitness fitness,
                   @NotNull final Mutator<T> mutator,
                   @NotNull final Recombiner<T> recombiner,
                   @NotNull final Selector selector) {
        this.population = population;
        this.fitness = fitness;
        this.mutator = mutator;
        this.recombiner = recombiner;
        this.selector = selector;
        evaluate();
        generation++;
    }

    public void evaluate(){
        population.evaluate(fitness);
    }

    public void record(Statistics<T> statistics) {
        population.record(statistics);
    }

    public void preOperate(PriorOperation<T> priorOperation){
        priorOperation.preSelect(population);
    }

    public void recombine(){
        while (!population.isReady()) {
            List<T> mates = population.select(selector);
            List<T> children = recombiner.recombine(mates);
            population.addChildren(children);
        }
    }

    public boolean nextGeneration(){
        generation++;
        return population.nextGeneration();
    }

    public void mutate(){
        population.mutate(mutator);
    }

    public Population<T> getPopulationCopy(){
        return population.copy();
    }

    /*
    public Map<String, List<T>> getElites(final int amount){
        return population.getElites(amount);
    }*/

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