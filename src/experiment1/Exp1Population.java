package experiment1;

import com.sun.istack.internal.NotNull;
import ga.collections.Population;
import ga.collections.Statistics;
import ga.components.chromosome.SequentialHaploid;
import ga.collections.Individual;
import ga.operations.Fitness;
import ga.operations.Selector;

import java.util.*;

/**
 * Created by david on 29/08/16.
 */
public class Exp1Population implements Population<SequentialHaploid> {

    private List<Individual<SequentialHaploid>> individuals;
    private List<Double> fitnessValues;
    private List<Individual<SequentialHaploid>> secondPool;
    private List<Individual<SequentialHaploid>> priorPool;
    private boolean evaluated = false;
    private boolean priorPoolMode = false;
    private int nextGenSize;
    private final int size;

    public Exp1Population(final int size) {
        individuals = new ArrayList<>(size);
        fitnessValues = new ArrayList<>(size);
        priorPool = new ArrayList<>(size);
        secondPool = new ArrayList<>(size);
        this.size = size;
        nextGenSize = 0;
    }

    private Exp1Population(List<Individual<SequentialHaploid>> individuals,
                           List<Double> fitnessValues,
                           List<Individual<SequentialHaploid>> secondPool,
                           List<Individual<SequentialHaploid>> priorPool,
                           boolean evaluated, boolean priorPoolMode, int size) {
        this.individuals = individuals;
        this.fitnessValues = fitnessValues;
        this.secondPool = secondPool;
        this.priorPool = priorPool;
        this.evaluated = evaluated;
        this.priorPoolMode = priorPoolMode;
        this.size = size;
        nextGenSize = secondPool.size() + priorPool.size();
    }

    @Override
    public Population<SequentialHaploid> copy() {
        List<Individual<SequentialHaploid>> individuals = new ArrayList<>(this.individuals);
        List<Double> fitnessValues = new ArrayList<>(this.fitnessValues);
        List<Individual<SequentialHaploid>> secondPool = new ArrayList<>(this.secondPool);
        List<Individual<SequentialHaploid>> priorPool = new ArrayList<>(this.priorPool);

        return new Exp1Population(individuals, fitnessValues, secondPool, priorPool, evaluated, priorPoolMode, size);
    }

    @Override
    public void evaluate(@NotNull final Fitness fitness) {

        if (!fitnessValues.isEmpty())
            fitnessValues.clear();

        double sum = 0;

        for (Individual<SequentialHaploid> h : individuals) {
            h.evaluate(fitness);
            fitnessValues.add(h.getFitness());
            sum += h.getFitness();
        }

        // Normalization
        // ** Fitness function has to be non-negative
        for (int i = 0; i < size; i++) {
            fitnessValues.set(i, fitnessValues.get(i)/sum);
        }
        Collections.sort(individuals);
        Collections.sort(fitnessValues);
        Collections.reverse(individuals);
        Collections.reverse(fitnessValues);
        evaluated = true;
    }

    @Override
    public void addChild(@NotNull Individual<SequentialHaploid> child) {
        if (nextGenSize < size) {
            if (priorPoolMode) priorPool.add(child);
            else secondPool.add(child);
            nextGenSize++;
        }
    }

    @Override
    public void setPriorPoolMode(final boolean mode) {
        priorPoolMode = mode;
    }

    @Override
    public void record(@NotNull final Statistics<SequentialHaploid> statistics) {
        if (!evaluated)
            return;
        statistics.record(individuals);
    }

    @Override
    public boolean isReady() {
        return nextGenSize == size;
    }

    @Override
    public boolean nextGeneration() {
        if (!isReady())
            return false;
        individuals.clear();
        individuals.addAll(priorPool);
        individuals.addAll(secondPool);
        priorPool.clear();
        secondPool.clear();
        nextGenSize = 0;
        fitnessValues.clear();
        evaluated = false;
        return true;
    }

    @Override
    public List<Individual<SequentialHaploid>> getIndividualsView() {
        return Collections.unmodifiableList(individuals);
    }

    @Override
    public List<Individual<SequentialHaploid>> getPriorPoolView() {
        return Collections.unmodifiableList(priorPool);
    }

    @Override
    public List<Individual<SequentialHaploid>> getSecondPoolView() {
        return Collections.unmodifiableList(secondPool);
    }

    @Override
    public List<Double> getFitnessValuesView() {
        return Collections.unmodifiableList(fitnessValues);
    }

    @Override
    public List<SequentialHaploid> selectMates(@NotNull final Selector selector) {
        final int index1 = selector.select(fitnessValues);
        int index2 = selector.select(fitnessValues);
        while (index2 == index1)
            index2 = selector.select(fitnessValues);
        List<SequentialHaploid> mates = new ArrayList<>(2);
        mates.add(individuals.get(index1).getChromosome());
        mates.add(individuals.get(index2).getChromosome());
        return mates;
    }
}
