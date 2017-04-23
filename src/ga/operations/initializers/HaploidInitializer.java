package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.genes.DataGene;
import ga.components.materials.GeneRegulatoryNetworkFactory;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;

/**
 * Created by Zhenyue Qin on 23/04/2017.
 * The Australian National University.
 */
public class HaploidInitializer implements Initializer<SimpleHaploid> {
    private int size;
    private int networkSize;
    private final SimpleMaterial target;
    private final int edgeSize;

    public HaploidInitializer(final int size, final int[] target, final int edgeSize) {
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

    private void probabilityFilter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
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
    public Population<SimpleHaploid> initialize() {
        Population<SimpleHaploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateIndividual());
        }
        population.nextGeneration();
        return population;
    }

    private Individual<SimpleHaploid> generateIndividual() {
        GeneRegulatoryNetworkFactory grnFactor = new GeneRegulatoryNetworkFactory(this.target, this.edgeSize);
        return new Individual<>(new SimpleHaploid(grnFactor.generateGeneRegulatoryNetwork()));
    }
}
