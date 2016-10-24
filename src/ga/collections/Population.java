package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.others.Copyable;

import java.util.*;

/**
 * This class represents a population used in genetic algorithms.
 * It consists of several pools for performing different kinds of operation to produce the next generation.
 * The PopulationMode values are used to indicate the current added child to be placed in corresponding pools.
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public class Population<C extends Chromosome> implements Copyable<Population<C>> {

    protected List<Individual<C>> individuals;
    protected List<Individual<C>> priorPool;
    protected List<Individual<C>> offspringPool;
    protected List<Individual<C>> postPool;
    protected Set<Integer> survivedIndices;

    protected PopulationMode mode;
    protected int nextGenSize;
    protected final int size;

    /**
     * Constructs an empty population.
     *
     * @param size population size
     */
    public Population(final int size) {
        individuals = new ArrayList<>(size);
        priorPool = new ArrayList<>(size);
        offspringPool = new ArrayList<>(size);
        postPool = new ArrayList<>(size);
        survivedIndices = new HashSet<>();
        this.size = size;
        nextGenSize = 0;
        mode = PopulationMode.REPRODUCE;
    }

    protected Population(@NotNull final List<Individual<C>> individuals,
                       final int size) {
        this.individuals = individuals;
        this.priorPool = new ArrayList<>(size);
        this.offspringPool = new ArrayList<>(size);
        this.postPool = new ArrayList<>(size);
        this.survivedIndices = new HashSet<>();
        this.mode = PopulationMode.REPRODUCE;
        this.nextGenSize = 0;
        this.size = size;
    }

    /**
     * Adds an individual to the corresponding pool with the given chromosomes.
     * @param chromosome chromosomes of the candidate individual
     */
    public void addCandidateChromosome(@NotNull final C chromosome) {
        addCandidate(new Individual<>(chromosome));
    }

    /**
     * Adds multiple individuals to the corresponding pool with the given chromosomes.
     * @param chromosomes a list of chromosomes of the candidate individuals
     */
    public void addCandidateChromosomes(@NotNull final List<C> chromosomes) {
        for (C child : chromosomes) addCandidateChromosome(child);
    }

    /**
     * Adds multiple candidates to the corresponding pool.
     * @param candidates a list of candidate individuals
     */
    public void addCandidates(@NotNull final List<Individual<C>> candidates) {
        for (Individual<C> child : candidates) addCandidate(child);
    }

    /**
     * Evaluates the fitnessFunctions function values of the individuals
     * @param fitnessFunction fitnessFunctions function
     * @param recompute determines whether to force re-computation of phenotype from genotype
     */
    public void evaluate(@NotNull final FitnessFunction fitnessFunction, final boolean recompute) {
        for (Individual<C> i : individuals)
            i.evaluate(fitnessFunction, recompute);
        Collections.sort(individuals);
    }

    /**
     * Adds a child/candidate to a pool for the next generation according to the mode.
     * @param candidate candidate to be added
     */
    public void addCandidate(@NotNull final Individual<C> candidate) {
        if (isReady()) return;
        switch (mode) {
            case PRIOR:
                priorPool.add(candidate);
                break;
            case REPRODUCE:
                offspringPool.add(candidate);
                break;
            case POST:
                postPool.add(candidate);
                break;
        }
        nextGenSize++;
    }

    /**
     * Sets the mode to be PRIOR, REPRODUCE, or POST
     * @param mode
     */
    public void setMode(final PopulationMode mode) {
        this.mode = mode;
    }

    /**
     * Marks the index of an individual that survives to the next generation.
     * @param index
     */
    public void markSurvivedIndex(final int index) {
        survivedIndices.add(index);
    }

    /**
     * @return a boolean that states whether the population is ready to proceed to the next generation
     */
    public boolean isReady() {
        return nextGenSize == size;
    }

    /**
     * Proceeds the population to the next generation, if the population is ready.
     * @return a boolean that states whether the population is successfully proceeded to the next generation
     */
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
        return true;
    }

    /**
     * Returns the population size.
     * @return population size
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the total number of individuals in the pools.
     * @return total number of individuals in the pools
     */
    public int getNextGenSize() {
        return nextGenSize;
    }

    /**
     * Returns an unmodifiable view of the individuals.
     * @return unmodifiable view of the individuals
     */
    public List<Individual<C>> getIndividualsView() {
        return Collections.unmodifiableList(individuals);
    }

    /**
     * Returns an unmodifiable view of the prior pool.
     * @return unmodifiable view of the prior pool
     */
    public List<Individual<C>> getPriorPoolView() {
        return Collections.unmodifiableList(priorPool);
    }

    /**
     * Returns an unmodifiable view of the offspring pool.
     * @return unmodifiable view of the offspring pool
     */
    public List<Individual<C>> getOffspringPoolView() {
        return Collections.unmodifiableList(offspringPool);
    }

    /**
     * Returns an unmodifiable view of the post pool.
     * @return unmodifiable view of the post pool
     */
    public List<Individual<C>> getPostPoolView() {
        return Collections.unmodifiableList(postPool);
    }

    /**
     * Returns an unmodifiable view of the survivors indices.
     * @return unmodifiable view of the survivors indices
     */
    public Set<Integer> getSurvivedIndicesView() {
        return Collections.unmodifiableSet(survivedIndices);
    }

    @Override
    public Population<C> copy() {
        List<Individual<C>> individualsCopy = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            individualsCopy.add(individuals.get(i).copy());
        }
        return new Population<>(individualsCopy, size);
    }
}