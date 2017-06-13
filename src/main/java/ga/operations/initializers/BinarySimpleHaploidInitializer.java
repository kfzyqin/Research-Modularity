package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.genes.BinaryGene;
import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.List;

/*
    GASEE is a Java-based genetic algorithm library for scientific exploration and experiment.
    Copyright 2016 Siu-Kei Muk

    This file is part of GASEE.

    GASEE is free library: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 2.1 of the License, or
    (at your option) any later version.

    GASEE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with GASEE.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * This class is a simple initializer that initializes a population of binary simple haploid individuals.
 * The probability governs the likelyhood of a randomly generated gene to contain the value 1.
 *
 * @author Siu Kei Muk (David)
 * @since 8/09/16.
 */
public class BinarySimpleHaploidInitializer implements Initializer<SimpleHaploid> {

    private int size;
    private int length;
    private double probability = 0.5;

    /**
     * Constructs the initializer
     * @param size size of population to be initialized
     * @param length length of each haploid
     */
    public BinarySimpleHaploidInitializer(final int size, final int length) {
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
            population.addCandidate(generateIndividual());
        }
        population.nextGeneration();
        return population;
    }

    private Individual<SimpleHaploid> generateIndividual() {
        List<Gene<Integer>> genes = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            genes.add((Math.random() < probability) ? new BinaryGene(1) : new BinaryGene(0));
        }
        return new Individual<>(new SimpleHaploid(new SimpleMaterial(genes)));
    }
}
