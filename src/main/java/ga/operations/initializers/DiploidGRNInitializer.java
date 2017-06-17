package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.genes.DataGene;
import ga.components.materials.GeneRegulatoryNetwork;
import ga.components.materials.GeneRegulatoryNetworkFactory;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;
import ga.operations.expressionMaps.SimpleDiploidRandomMap;

import java.util.ArrayList;

/**
 * Created by Zhenyue Qin on 6/06/2017.
 * The Australian National University.
 */
public class DiploidGRNInitializer implements Initializer<SimpleDiploid> {
    private int size;
    private final SimpleMaterial target;
    private final int edgeSize;
    private final double dominanceMutationRate;

    public DiploidGRNInitializer(final int size, final int[] target, final int edgeSize, double dominanceMutationRate) {
        setSize(size);
        ArrayList<DataGene> tempTargetList = new ArrayList<>();
        for (int i=0; i<target.length; i++) {
            tempTargetList.add(new DataGene(target[i]));
        }
        this.target = new SimpleMaterial(tempTargetList);
        this.edgeSize = edgeSize;
        this.dominanceMutationRate = dominanceMutationRate;
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

    private void filter(final int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Size must be at least one");
        }
    }

    @Override
    public Population<SimpleDiploid> initialize() {
        Population<SimpleDiploid> population = new Population<>(size);
        for (int i = 0; i < size; i++) {
            population.addCandidate(generateIndividual());
        }
        population.nextGeneration();
        return population;
    }

    private Individual<SimpleDiploid> generateIndividual() {
        GeneRegulatoryNetworkFactory grnFactor = new GeneRegulatoryNetworkFactory(this.target, this.edgeSize);
        ExpressionMap<SimpleMaterial,SimpleMaterial> mapping = new DiploidEvolvedMap(this.target.getSize() * this.target.getSize());
        GeneRegulatoryNetwork dna1 = grnFactor.generateGeneRegulatoryNetwork();
        GeneRegulatoryNetwork dna2 = grnFactor.generateGeneRegulatoryNetwork();
        return new Individual<>(new SimpleDiploid(dna1, dna2, mapping));
    }
}
