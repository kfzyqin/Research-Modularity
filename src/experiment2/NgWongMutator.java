package experiment2;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.genes.Gene;
import ga.components.materials.GeneticMaterial;
import ga.operations.mutators.Mutator;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 11/09/16.
 */
public class NgWongMutator implements Mutator<SimpleDiploid> {

    private static final char[] values = {'0','o','1','i'};
    private double probability;

    public NgWongMutator(final double probability) {
        setProbability(probability);
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(final double probability) {
        filter(probability);
        this.probability = probability;
    }

    @Override
    public void mutate(@NotNull final List<Individual<SimpleDiploid>> individuals) {
        for (Individual<SimpleDiploid> individual : individuals) {
            final GeneticMaterial material1 = individual.getChromosome().getMaterialsView().get(0);
            final GeneticMaterial material2 = individual.getChromosome().getMaterialsView().get(1);
            final int length = material1.getSize();
            for (int i = 0; i < length; i++) {
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

    private char generateValue(final char invalidChar) {
        char value = values[ThreadLocalRandom.current().nextInt(values.length)];
        while (value == invalidChar) {
            value = values[ThreadLocalRandom.current().nextInt(values.length)];
        }
        return value;
    }
}
