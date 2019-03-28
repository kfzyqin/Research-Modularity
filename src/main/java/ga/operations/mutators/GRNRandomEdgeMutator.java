package ga.operations.mutators;

import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.components.materials.SimpleMaterial;

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
public class GRNRandomEdgeMutator<T extends Chromosome> extends GRNEdgeMutator<T> {
    private double prob;

    public GRNRandomEdgeMutator(final double prob) {
        super(prob);
    }

    public void mutateAGRN(SimpleMaterial material) {
        int targetNumber = (int) Math.sqrt(material.getSize());

        for (int i=0; i<targetNumber; i++) {
                    /* Number of regulators for gene i */
            int regulatorNumber = 0;

                    /* Does not meet the mutation rate */
            if (Math.random() > this.prob) {
                continue;
            }

                    /* Get how many genes that regulate gene i */
            for (int j=0; j<targetNumber; j++) {
                if ((int) material.getGene(j * targetNumber + i).getValue() != 0) {
                    regulatorNumber += 1;
                }
            }

            double probabilityToLoseInteraction = 0.5;

            actualMutate(probabilityToLoseInteraction, material, targetNumber, i);
        }
    }
}
