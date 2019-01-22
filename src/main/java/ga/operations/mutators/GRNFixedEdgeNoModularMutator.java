package ga.operations.mutators;

import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.components.materials.SimpleMaterial;
import tools.PatternTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements a mutator to mutate a GRN.
 *
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class GRNFixedEdgeNoModularMutator<T extends Chromosome> implements Mutator<T> {
    private double prob;

    public GRNFixedEdgeNoModularMutator(final double prob) {
        filter(prob);
        this.prob = prob;
    }

    private void filter(final double prob) {
        if (prob < 0 || prob > 1)
            throw new IllegalArgumentException("Mutation probability must be between 0 and 1.");
    }

    public double getProbability() {
        return prob;
    }

    public void setProbability(final double prob) {
        filter(prob);
        this.prob = prob;
    }

    public void mutateAGRN(SimpleMaterial material) {
        int targetNumber = (int) Math.sqrt(material.getSize());

        List<Integer> zeroPositions = new ArrayList<>();
        List<Integer> edgePositions = new ArrayList<>();

        for (int i=0; i<material.getSize(); i++) {
            int currentRow = (int) (i / targetNumber);
            int currentCol = i % targetNumber;
            if ((int) material.getGene(i).getValue() != 0) {
                edgePositions.add(i);
            } else {
                if (currentRow < (targetNumber / 2) && currentCol < (targetNumber / 2) || currentRow > (targetNumber / 2) && currentCol > (targetNumber / 2))
                zeroPositions.add(i);
            }
        }

        for (int i=0; i<targetNumber; i++) {
            if (Math.random() > this.prob) {
                continue;
            }

            int eIdx = ThreadLocalRandom.current().nextInt(edgePositions.size());
            int zIdx = ThreadLocalRandom.current().nextInt(zeroPositions.size());
            material.getGene(edgePositions.get(eIdx)).setValue(0);
            int newEdgeCol = zeroPositions.get(zIdx) % targetNumber;
            int newEdgeRow = (int) (zeroPositions.get(zIdx) / targetNumber);

            material.getGene(zeroPositions.get(zIdx)).setValue(PatternTemplate.getPatternTemplate()[newEdgeRow][newEdgeCol]);
        }
    }

    /**
     * Mutates the specified gene at index i according to the rule specified in page 8 of the original paper.
     * @param individuals individuals to be mutated
     */
    @Override
    public void mutate(List<Individual<T>> individuals) {
        for (Individual<T> individual : individuals) {
            for (Object object : individual.getChromosome().getMaterialsView()) {
                SimpleMaterial material = (SimpleMaterial) object;
                mutateAGRN(material);
            }
        }

    }

    private boolean flipACoin() {
        return 0.5 < Math.random();
    }

    private int generateAnRandomInteger(int upBound) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(upBound);
    }
}
