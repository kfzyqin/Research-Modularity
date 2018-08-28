package ga.collections;

import ga.components.chromosomes.Chromosome;
import ga.components.genes.DataGene;
import ga.components.materials.GRN;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.FitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.others.Copyable;
import org.jetbrains.annotations.NotNull;


import java.io.Serializable;
import java.util.List;

/**
 * This class represents an individual in the population. An individual consists of a chromosomes and fitnessFunctions function value.
 * This class implements Comparable for sorting purpose (in descending order).
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public class Individual<C extends Chromosome> implements Comparable<Individual<C>>, Copyable<Individual<C>>, Serializable {

    protected C chromosome;
    protected double fitness = 0;

    public List<DataGene[][]> individualSPerturbations;

    /**
     * Constructs an individual.
     * @param chromosome chromosomes of the individual
     */
    public Individual(@NotNull final C chromosome) {
        this.chromosome = chromosome;
    }

    protected Individual(@NotNull final C chromosome, final double fitness) {
        this.chromosome = (C)chromosome.copy();
        this.fitness = fitness;
    }

    protected Individual(@NotNull final C chromosome, final double fitness, List<DataGene[][]> perturbations) {
        this.chromosome = (C)chromosome.copy();
        this.fitness = fitness;
        this.individualSPerturbations = perturbations;
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

    public List<DataGene[][]> getIndividualSPerturbations() {
        return this.individualSPerturbations;
    }

    @Override
    public Individual<C> copy() {
        return new Individual<>((C)chromosome.copy(), fitness, this.individualSPerturbations);
    }

    public double evaluate(final FitnessFunction objective, final boolean recompute) {
        fitness = objective.evaluate(chromosome.getPhenotype(recompute));
        this.individualSPerturbations = GRNFitnessFunctionMultipleTargets.currentPerturbations;
        return fitness;
    }

    public double evaluate(final FitnessFunctionMultipleTargets objective, final boolean recompute, int generation) {
        fitness = objective.evaluate(chromosome.getPhenotype(recompute), generation);
        this.individualSPerturbations = GRNFitnessFunctionMultipleTargets.currentPerturbations;
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

    public double getGRNModularity() {
        return ((GRN) this.getChromosome().getPhenotype(false)).getGRNModularity();
    }
}
