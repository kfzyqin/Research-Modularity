package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;
import ga.operations.fitness.Fitness;
import ga.operations.initializers.Initializer;
import ga.operations.selectors.Selector;
import ga.others.Copyable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public class Population<T extends Chromosome> implements Copyable<Population<T>> {

    private List<Individual<T>> individuals;
    private List<Individual<T>> priorPool;
    private List<Individual<T>> secondPool;
    // private List<Double> normalizedFitnessValues;

    // private boolean evaluated;
    private boolean priorPoolMode;
    private int nextGenSize;
    private final int size;

    public Population(final int size) {
        individuals = new ArrayList<>(size);
        priorPool = new ArrayList<>(size);
        secondPool = new ArrayList<>(size);
        // normalizedFitnessValues = new ArrayList<>(size);
        this.size = size;
        nextGenSize = 0;
        priorPoolMode = false;
    }

    private Population(@NotNull final List<Individual<T>> individuals,
                       // @NotNull final List<Double> normalizedFitnessValues,
                       final int size) {
        this.individuals = individuals;
        this.priorPool = new ArrayList<>(size);
        this.secondPool = new ArrayList<>(size);
        // this.normalizedFitnessValues = normalizedFitnessValues;
        this.priorPoolMode = false;
        this.nextGenSize = 0;
        this.size = size;
    }

    public void addChildChromosome(@NotNull final T child) {
        addChild(new Individual<>(child));
    }

    public void addChildrenChromosome(@NotNull final List<T> children) {
        for (T child : children) addChildChromosome(child);
    }

    public void addChildren(@NotNull final List<Individual<T>> children) {
        for (Individual<T> child : children) addChild(child);
    }

    public void evaluate(@NotNull final Fitness fitness){
        for (Individual<T> i : individuals)
            i.evaluate(fitness);
        Collections.sort(individuals);
        Collections.reverse(individuals);
    }

    public void addChild(@NotNull final Individual<T> child) {
        if (isReady()) return;
        if (priorPoolMode) priorPool.add(child);
        else secondPool.add(child);
        nextGenSize++;
    }

    public void setPriorPoolMode(final boolean mode) {
        this.priorPoolMode = mode;
    }

    /*
    public void record(@NotNull final Statistics<T> statistics) {
        if (!evaluated) return;
        statistics.record(individuals);
    }*/

    public boolean isReady() {
        return nextGenSize == size;
    }

    public boolean nextGeneration() {
        if (!isReady())
            return false;
        individuals.clear();
        individuals.addAll(priorPool);
        individuals.addAll(secondPool);
        priorPool.clear();
        secondPool.clear();
        nextGenSize = 0;
        // normalizedFitnessValues.clear();
        return true;
    }

    public List<Individual<T>> getIndividualsView() {
        return Collections.unmodifiableList(individuals);
    }

    public List<Individual<T>> getPriorPoolView(){
        return Collections.unmodifiableList(priorPool);
    }

    public List<Individual<T>> getSecondPoolView() {
        return Collections.unmodifiableList(secondPool);
    }

    /*
    public List<Double> getNormalizedFitnessValuesView() {
        return Collections.unmodifiableList(normalizedFitnessValues);
    }*/

    @Override
    public Population<T> copy() {
        List<Individual<T>> individualsCopy = new ArrayList<>(size);
        // List<Double> normalizedFitnessValuesCopy = new ArrayList<>(normalizedFitnessValues);
        for (int i = 0; i < size; i++) {
            individualsCopy.add(individuals.get(i).copy());
        }
        return new Population<>(individualsCopy, size); //, normalizedFitnessValuesCopy, size);
    }

    // public abstract List<T> selectMates(@NotNull final Selector selector);
}
