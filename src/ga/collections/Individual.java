package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;
import ga.operations.fitness.Fitness;
import ga.others.Copyable;

/**
 * Created by david on 27/08/16.
 */
public class Individual<T extends Chromosome> implements Comparable<Individual<T>>, Copyable<Individual<T>> {

    private T chromosome;
    private double fitness = 0;

    public Individual(@NotNull final T chromosome) {
        this.chromosome = chromosome;
    }

    private Individual(@NotNull final T chromosome, final double fitness) {
        this.chromosome = (T)chromosome.copy();
        this.fitness = fitness;
    }

    @Override
    public int compareTo(final Individual<T> tIndividual) {
        final double tFitness = tIndividual.getFitness();
        if (fitness > tFitness)
            return 1;
        else if (fitness < tFitness)
            return -1;
        else
            return 0;
    }

    @Override
    public Individual<T> copy() {
        return new Individual<>(chromosome, fitness);
    }

    public double evaluate(final Fitness objective, final boolean recompute) {
        fitness = objective.evaluate(chromosome.getPhenotype(recompute));
        return fitness;
    }

    public double getFitness(){
        return fitness;
    }

    public T getChromosome() {
        return chromosome;
    }

    @Override
    public String toString() {
        return "Fitness: " + fitness + ", " + chromosome.toString();
    }
}
