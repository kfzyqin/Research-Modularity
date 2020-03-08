package tests;

import ga.components.genes.DataGene;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsBalanceAsymmetric;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.List;

public class GRNMultiTargetTest {
    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };
    private static final int maxCycle = 30;
    private static final double perturbationRate = 0.15;
    private static final List<Integer> thresholds = Arrays.asList(0, 500);
    private static final double stride = 0.00;

    public static void main(String[] args) {
        int[][] targets = {target1, target2};

        GRNFitnessFunctionMultipleTargetsBalanceAsymmetric fitnessFunctionESW = new GRNFitnessFunctionMultipleTargetsBalanceAsymmetric(
                targets, maxCycle, 500, perturbationRate, thresholds, stride);

        GRNFitnessFunctionMultipleTargets fitnessFunctionESWOrig = new GRNFitnessFunctionMultipleTargets(
                targets, maxCycle, 500, perturbationRate, thresholds);

        int[] aTmp = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        DataGene[] dataGenes = new DataGene[10];
        for (int i=0; i<aTmp.length; i++) {
            dataGenes[i] = new DataGene(aTmp[i]);
        }

        System.out.println(fitnessFunctionESW.getHammingDistance(dataGenes, target1));
    }



}
