package examples.experiment3;

import ga.components.genes.BinaryGene;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;
import ga.operations.expressionMaps.SimpleDiploidRandomMap;
import ga.operations.initializers.Initializer;
import genderGAWithHotspots.collections.GenderPopulation;
import genderGAWithHotspots.components.chromosomes.SimpleGenderDiploid;
import genderGAWithHotspots.components.hotspots.DiscreteExpHotspot;
import genderGAWithHotspots.components.hotspots.Hotspot;

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
 * @since 30/09/16.
 */
public class Exp3Initializer implements Initializer<SimpleGenderDiploid<Integer>> {

    private int size;
    private int length;

    public Exp3Initializer(final int size, final int length) {
        setSize(size);
        filter(length);
        this.length = length;
    }

    private void filter(final int size) {
        if (size < 1) throw new IllegalArgumentException("Size must be at least one.");
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

    @Override
    public GenderPopulation<SimpleGenderDiploid<Integer>> initialize() {
        GenderPopulation<SimpleGenderDiploid<Integer>> population = new GenderPopulation<>(size);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new SimpleDiploidRandomMap(0.5);
        while (!population.isReady()) {
            Hotspot<Integer> hotspot = new DiscreteExpHotspot(length, 6, 1);
            SimpleMaterial dna1 = generate();
            SimpleMaterial dna2 = generate();
            population.addCandidateChromosome(new SimpleGenderDiploid<>(dna1,dna2,mapping,hotspot, Math.random() < 0.5));
        }
        population.nextGeneration();
        return population;
    }

    private SimpleMaterial generate() {
        List<BinaryGene> genes = new ArrayList<>(length);
        for (int i = 0; i < length; i++) genes.add(BinaryGene.generateRandomBinaryGene());
        return new SimpleMaterial(genes);
    }
}
