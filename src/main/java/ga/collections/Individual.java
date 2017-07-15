package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.FitnessFunctionMultipleTargets;
import ga.others.Copyable;

/**
 * This class represents an individual in the population. An individual consists of a chromosomes and fitnessFunctions function value.
 * This class implements Comparable for sorting purpose (in descending order).
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public class Individual<C extends Chromosome> implements Comparable<Individual<C>>, Copyable<Individual<C>> {

    private C chromosome;
    private double fitness = 0;

    /**
     * Constructs an individual.
     * @param chromosome chromosomes of the individual
     */
    public Individual(@NotNull final C chromosome) {
        this.chromosome = chromosome;
    }

    private Individual(@NotNull final C chromosome, final double fitness) {
        this.chromosome = (C)chromosome.copy();
        this.fitness = fitness;
    }

    @Override
    public int compareTo(final Individual<C> tIndividual) {
        final double tFitness = tIndividual.getFitness();
        // For descending sort
        if (fitness > tFitness)
            return -1;
        else if (fitness < tFitness)
            return 1;
        else
            return 0;
    }

    @Override
    public Individual<C> copy() {
        return new Individual<>((C)chromosome.copy(), fitness);
    }

    public double evaluate(final FitnessFunction objective, final boolean recompute) {
        fitness = objective.evaluate(chromosome.getPhenotype(recompute));
        return fitness;
    }

    public double evaluate(final FitnessFunctionMultipleTargets objective, final boolean recompute, int generation) {
        fitness = objective.evaluate(chromosome.getPhenotype(recompute), generation);
        return fitness;
    }

    public double getFitness(){
        return fitness;
    }

    public C getChromosome() {
        return chromosome;
    }

    @Override
    public String toString() {
        return "FitnessFunction: " + fitness + ", " + chromosome.toString();
    }
}
