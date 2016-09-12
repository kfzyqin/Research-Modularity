package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosome.SimpleHaploid;
import ga.components.genes.BinaryGene;
import ga.components.genes.Gene;
import ga.components.materials.SimpleDNA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 8/09/16.
 */
public class BinarySequentialHaploidInitializer implements Initializer<SimpleHaploid> {

    private int size;
    private int length;
    private double probability = 0.5;

    public BinarySequentialHaploidInitializer(final int size, final int length) {
        this.size = size;
        this.length = length;
    }

    private void filter(final int value) {
        if (value < 1)
            throw new IllegalArgumentException("Invalid value: Input must not be less than 1.");
    }

    private void probabilityFilter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    public int getLength() {
        return length;
    }

    public void setLength(final int length) {
        filter(length);
        this.length = length;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(final int size) {
        filter(size);
        this.size = size;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(final double probability) {
        probabilityFilter(probability);
        this.probability = probability;
    }

    @Override
    public Population<SimpleHaploid> initialize() {
        Population<SimpleHaploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addChild(generateIndividual());
        }
        population.nextGeneration();
        return population;
    }

    private Individual<SimpleHaploid> generateIndividual() {
        List<Gene<Integer>> genes = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            genes.add((Math.random() < probability) ? new BinaryGene(1) : new BinaryGene(0));
        }
        return new Individual<>(new SimpleHaploid(new SimpleDNA(genes)));
    }
}
