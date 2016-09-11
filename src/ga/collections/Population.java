package ga.collections;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.bind.v2.runtime.Coordinator;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import ga.components.chromosome.Chromosome;
import ga.operations.fitness.Fitness;
import ga.others.Copyable;

import java.util.*;

/**
 * Created by david on 27/08/16.
 */
public class Population<T extends Chromosome> implements Copyable<Population<T>> {

    private List<Individual<T>> individuals;
    private List<Individual<T>> priorPool;
    private List<Individual<T>> offspringPool;
    private List<Individual<T>> postPool;
    private Set<Integer> survivedIndices;
    // private List<Double> normalizedFitnessValues;

    // private boolean evaluated;
    private PopulationMode mode;
    private int nextGenSize;
    private final int size;

    public Population(final int size) {
        individuals = new ArrayList<>(size);
        priorPool = new ArrayList<>(size);
        offspringPool = new ArrayList<>(size);
        postPool = new ArrayList<>(size);
        survivedIndices = new HashSet<>();
        // normalizedFitnessValues = new ArrayList<>(size);
        this.size = size;
        nextGenSize = 0;
        mode = PopulationMode.RECOMBINE;
    }

    private Population(@NotNull final List<Individual<T>> individuals,
                       // @NotNull final List<Double> normalizedFitnessValues,
                       final int size) {
        this.individuals = individuals;
        this.priorPool = new ArrayList<>(size);
        this.offspringPool = new ArrayList<>(size);
        this.postPool = new ArrayList<>(size);
        this.survivedIndices = new HashSet<>();
        // this.normalizedFitnessValues = normalizedFitnessValues;
        this.mode = PopulationMode.RECOMBINE;
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
        switch (mode) {
            case PRIOR:
                priorPool.add(child);
                break;
            case RECOMBINE:
                offspringPool.add(child);
                break;
            case POST:
                postPool.add(child);
                break;
        }
        nextGenSize++;
    }

    public void setMode(final PopulationMode mode) {
        this.mode = mode;
    }

    public void markSurvivedIndex(final int index) {
        survivedIndices.add(index);
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
        individuals.addAll(offspringPool);
        individuals.addAll(postPool);
        priorPool.clear();
        offspringPool.clear();
        postPool.clear();
        survivedIndices.clear();
        nextGenSize = 0;
        // normalizedFitnessValues.clear();
        return true;
    }

    public int getSize() {
        return size;
    }

    public int getNextGenSize() {
        return nextGenSize;
    }

    public List<Individual<T>> getIndividualsView() {
        return Collections.unmodifiableList(individuals);
    }

    public List<Individual<T>> getPriorPoolView(){
        return Collections.unmodifiableList(priorPool);
    }

    public List<Individual<T>> getOffspringPoolView() {
        return Collections.unmodifiableList(offspringPool);
    }

    public List<Individual<T>> getPostPoolView() { return Collections.unmodifiableList(postPool);}

    public Set<Integer> getSurvivedIndicesView() {return Collections.unmodifiableSet(survivedIndices);}

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
