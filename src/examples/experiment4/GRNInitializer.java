package examples.experiment4;

import com.sun.javafx.geom.Edge;
import ga.collections.Population;
import ga.components.genes.EdgeGene;
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

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class GRNInitializer implements Initializer<SimpleGenderDiploid<Integer>>{

    private int size;
    private int length;

    public GRNInitializer(final int size, final int length) {
        setSize(size);
        filter(length);
        this.length = length;
    }

    private void filter(final int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size must be at least one");
        }
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setSize(int size) {
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
        List<EdgeGene> genes = new ArrayList<>(length);
        for (int i=0; i<length; i++) {
            genes.add(EdgeGene.generateRandomEdgeGene());
        }
        return new SimpleMaterial(genes);
    }
}
