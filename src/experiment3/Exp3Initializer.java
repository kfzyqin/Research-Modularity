package experiment3;

import ga.operations.dominanceMap.SimpleDiploidRandomMap;
import genderGAWithHotspots.collections.GenderPopulation;
import genderGAWithHotspots.components.chromosomes.SimpleGenderDiploid;
import ga.components.genes.BinaryGene;
import genderGAWithHotspots.components.hotspots.DiscreteExpHotspot;
import genderGAWithHotspots.components.hotspots.Hotspot;
import ga.components.materials.SimpleDNA;
import ga.operations.dominanceMap.DominanceMap;
import ga.operations.initializers.Initializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 30/09/16.
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
        DominanceMap<SimpleDNA,SimpleDNA> mapping = new SimpleDiploidRandomMap(0.5);
        while (!population.isReady()) {
            Hotspot<Integer> hotspot = new DiscreteExpHotspot(length, 6, 1);
            SimpleDNA dna1 = generate();
            SimpleDNA dna2 = generate();
            population.addCandidateChromosome(new SimpleGenderDiploid<>(dna1,dna2,mapping,hotspot, Math.random() < 0.5));
        }
        population.nextGeneration();
        return population;
    }

    private SimpleDNA generate() {
        List<BinaryGene> genes = new ArrayList<>(length);
        for (int i = 0; i < length; i++) genes.add(BinaryGene.generateRandomBinaryGene());
        return new SimpleDNA(genes);
    }
}
