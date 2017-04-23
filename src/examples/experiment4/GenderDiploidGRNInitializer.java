package examples.experiment4;

import com.sun.javafx.geom.Edge;
import ga.collections.Population;
import ga.components.genes.DataGene;
import ga.components.genes.EdgeGene;
import ga.components.materials.GeneRegulatoryNetwork;
import ga.components.materials.GeneRegulatoryNetworkFactory;
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
public class GenderDiploidGRNInitializer implements Initializer<SimpleGenderDiploid<Integer>>{

    private int size;
    private int networkSize;
    private final SimpleMaterial target;
    private final int edgeSize;

    public GenderDiploidGRNInitializer(final int size, final int[] target, final int edgeSize) {
        this.size = size;
        setSize(size);
        filter(size);
        this.networkSize = target.length * target.length;
        ArrayList<DataGene> tempTargetList = new ArrayList<>();
        for (int i=0; i<target.length; i++) {
            tempTargetList.add(new DataGene(target[i]));
        }
        this.target = new SimpleMaterial(tempTargetList);
        this.edgeSize = edgeSize;
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
            Hotspot<Integer> hotspot = new DiscreteExpHotspot(this.networkSize, 6, 1);
            GeneRegulatoryNetwork dna1 = generate();
            GeneRegulatoryNetwork dna2 = generate();
            population.addCandidateChromosome(new SimpleGenderDiploid<>(dna1,dna2,mapping,hotspot, Math.random() < 0.5));
        }
        population.nextGeneration();
        return population;
    }

    private GeneRegulatoryNetwork generate() {
        /*
        TODO: Make the number of edges be a specific number
         */
        GeneRegulatoryNetworkFactory grnFactory = new GeneRegulatoryNetworkFactory(this.target, this.edgeSize);
        return grnFactory.generateGeneRegulatoryNetwork();
    }
}
