package ga.operations.initializers;

import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.SimpleHaploid;
import ga.components.genes.EdgeGene;
import ga.components.genes.Gene;
import ga.components.materials.EdgeMaterial;
import ga.components.materials.GRN;
import ga.components.materials.SimpleMaterial;
import ga.others.GeneralMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreFixedIndividualInitializer extends HaploidGRNInitializer {
    public PreFixedIndividualInitializer(int size, int targetLength, int edgeSize) {
        super(size, targetLength, edgeSize);
    }

    @Override
    public Population<SimpleHaploid> initialize() {
        Population<SimpleHaploid> population = new Population<>(size);
//        String aModFile = "./data/perfect_modular_individuals.txt";
//        String aModFile = "/home/zhenyue-qin/Research/Project-Maotai-Modularity/jars/generated-outputs/proportional-100/2019-10-14-18-29-17/population-phenotypes/all-population-phenotype_gen_10.lists";
        String aModFile = "/Users/rouyijin/Desktop/initial-population.lists";
        List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);
        int count = 0;
        for (String[] aLine : lines) {
            SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(aLine);
            List<Gene> genes = new ArrayList<>(Arrays.asList(aMaterial.getStrand()));
            List<EdgeGene> edgeGenes = new ArrayList<>();
            for (int i=0; i<aMaterial.getSize(); i++) {
                edgeGenes.add(new EdgeGene((Integer) genes.get(i).getValue()));
            }
            GRN aGRN = new GRN(edgeGenes);
            SimpleHaploid hMaterial = new SimpleHaploid(aGRN);
            population.addCandidate(new Individual<>(hMaterial));
            count += 1;
            if (count == this.size) {
                population.nextGeneration();
                return population;
            }

        }
        throw new RuntimeException("Population size wrong. ");
    }
}
