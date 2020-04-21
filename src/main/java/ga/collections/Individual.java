package ga.collections;

import ga.components.chromosomes.Chromosome;
import ga.components.genes.DataGene;
import ga.components.materials.GRN;
import ga.components.materials.Material;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.FitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.initializers.Initializer;
import ga.others.Copyable;
import ga.others.GeneralMethods;
import org.jetbrains.annotations.NotNull;


import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean canRegulateToTarget(int[] target, DataGene[] perturbed, int maxCycle) {
        DataGene[] targetDataGene = new DataGene[target.length];
        for (int i = 0; i < target.length; i++) {
            targetDataGene[i] = new DataGene(target[i]);
        }

        if (canAchieveAttractor(perturbed, this.chromosome.getPhenotype(false), maxCycle)) {
            DataGene[] attractor = getAttractor(perturbed, this.chromosome.getPhenotype(false), maxCycle);
            return equalDataGeneArray(attractor, targetDataGene);
        } else return false;

    }

    public DataGene[] getAttractor(DataGene[] currentState, Material phenotype, int maxCycle) {
        DataGene[] updated = update(currentState, phenotype);
        if (canAchieveAttractor(currentState, phenotype, maxCycle)) {
            if (equalDataGeneArray(currentState, updated)) return updated;
            else return getAttractor(updated, phenotype, maxCycle-1);
        } else return null;
    }

    public boolean canAchieveAttractor(DataGene[] currentState, Material phenotype, int maxCycle) {
        DataGene[] updated = update(currentState, phenotype);
        if (equalDataGeneArray(updated, currentState)) return true;
        else if (maxCycle > 0) return canAchieveAttractor(updated, phenotype, maxCycle-1);
        else return false;
    }

    public DataGene[] update(DataGene[] currentState, Material phenotype) {
        DataGene[] updatedState = new DataGene[currentState.length];
        updatedState = this.initializeDataGeneArray(updatedState);
        for (int i=0; i<currentState.length; i++) {
            double influence = 0;
            for (int j=0; j<currentState.length; j++) {
                int aNewInfluence = (int)
                        (phenotype.getGene(i + j*updatedState.length)).getValue() * currentState[j].getValue();
                influence += aNewInfluence;
            }
            updatedState[i].setValue(influence > 0 ? 1 : -1);
        }
        return updatedState;
    }

    protected DataGene[] initializeDataGeneArray(DataGene[] dataGenes) {
        DataGene[] emptyDataGeneArray = new DataGene[dataGenes.length];
        for (int i=0; i<emptyDataGeneArray.length; i++) {
            emptyDataGeneArray[i] = new DataGene();
        }
        return emptyDataGeneArray;
    }

    public boolean equalDataGeneArray(DataGene[] current, DataGene[] updated) {
        int differenceCounts = 0;
        for (int i=0; i<current.length; i++) {
            if (current[i].getValue() != updated[i].getValue()) {
                differenceCounts += 1;
            }
        }
        return differenceCounts == 0;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Individual)) {
            return false;
        }
        return this.chromosome.equals(((Individual) obj).chromosome);
    }
}
