package experiment2;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosome.SimpleDiploid;
import ga.components.genes.Gene;
import ga.components.materials.GeneticMaterial;
import ga.operations.mutation.MutationOperator;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 12/09/16.
 */
public class RyanAdditiveMutationOperator implements MutationOperator<SimpleDiploid> {

    private static final char[] values = {'A','B','C','D'};
    private double probability;

    public RyanAdditiveMutationOperator(double probability) {
        setProbability(probability);
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        filter(probability);
        this.probability = probability;
    }

    @Override
    public void mutate(@NotNull List<Individual<SimpleDiploid>> individuals) {
        for (Individual<SimpleDiploid> individual : individuals) {
            GeneticMaterial material1 = individual.getChromosome().getMaterialsView().get(0);
            GeneticMaterial material2 = individual.getChromosome().getMaterialsView().get(1);
            for (int i = 0; i < material1.getSize(); i++) {
                if (Math.random() < probability) {
                    Gene gene = material1.getGene(i);
                    gene.setValue(generateValue((char)gene.getValue()));
                }

                if (Math.random() < probability) {
                    Gene gene = material2.getGene(i);
                    gene.setValue(generateValue((char)gene.getValue()));
                }
            }
        }
    }

    private char generateValue(final char invalidValue) {
        char value = values[ThreadLocalRandom.current().nextInt(values.length)];
        while (value == invalidValue)
            value = values[ThreadLocalRandom.current().nextInt(values.length)];
        return value;
    }
}
