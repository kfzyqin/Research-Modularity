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

public class PerfectIndividualInitializer extends HaploidGRNInitializer {
    public PerfectIndividualInitializer(int size, int targetLength, int edgeSize) {
        super(size, targetLength, edgeSize);
    }

    @Override
    public Population<SimpleHaploid> initialize() {
        Population<SimpleHaploid> population = new Population<>(size);
        String aModFile = "./Data/perfect_modular_individuals.txt";
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
