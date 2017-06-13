package examples.experiment2;

import ga.collections.Population;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;
import ga.operations.initializers.Initializer;

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
 *
 * @author Siu Kei Muk (David)
 * @since 11/09/16.
 */
public class RyanAdditiveInitializer implements Initializer<SimpleDiploid> {

    private static final char[] chars = {'A','B','C','D'};
    private int size;
    private int length;
    private RyanAdditiveGeneFactory factory = new RyanAdditiveGeneFactory();

    public RyanAdditiveInitializer(final int length, final int size) {
        filter(length, "Length must be at least 1.");
        setSize(size);
        this.length = length;
    }

    private void filter(final int num, String errStr) {
        if (num < 1) throw new IllegalArgumentException(errStr);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        filter(size, "Population size must be at least 1.");
        this.size = size;
    }

    @Override
    public Population<SimpleDiploid> initialize() {
        Population<SimpleDiploid> population = new Population<>(size);
        ExpressionMap mapping = new RyanAdditiveScheme();
        for (int i = 0; i < size; i++) {
            List<RyanAdditiveSchemeGene> genes1 = new ArrayList<>(length);
            List<RyanAdditiveSchemeGene> genes2 = new ArrayList<>(length);
            for (int j = 0; j < length; j++) {
                genes1.add(factory.generateGene());
                genes2.add(factory.generateGene());
            }
            population.addCandidateChromosome(new SimpleDiploid(new SimpleMaterial(genes1),
                                                                new SimpleMaterial(genes2),
                                                                mapping));
        }
        population.nextGeneration();
        return population;
    }
}
