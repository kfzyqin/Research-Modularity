package experiment2;

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
 * @since 8/09/16.
 */
public class NgWongInitializer implements Initializer<SimpleDiploid> {

    private int size;
    private int length;
    private NgWongGeneFactory factory = new NgWongGeneFactory();

    public NgWongInitializer(final int length, final int size) {
        setSize(size);
        filter(length, "Length");
        this.length = length;
    }

    private void filter(final int size, String errStr) {
        if (size < 1)
            throw new IllegalArgumentException(errStr + " must be at least 1.");
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        filter(size, "Population size");
        this.size = size;
    }

    @Override
    public Population<SimpleDiploid> initialize() {
        ExpressionMap<SimpleMaterial, SimpleMaterial> mapping = new NgWongSchemeMap();
        Population<SimpleDiploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            List<NgWongSchemeGene> genes1 = new ArrayList<>(length);
            List<NgWongSchemeGene> genes2 = new ArrayList<>(length);
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

    /*
    private NgWongSchemeGene generateGene() {
        final double R = .25;
        double r = Math.random();
        for (int i = 0; i < chars.length; i++) {
            if (r < R)
                return new NgWongSchemeGene(chars[i]);
            r -= R;
        }
        return new NgWongSchemeGene('i');
    }*/
}
