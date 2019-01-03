package experiments.experiment6;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.others.GeneralMethods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EdgeDeletionAnalyzer {
    public static int[] getBasicGRN(int[] aIntGRN, int startIdx) {
        if (startIdx >= aIntGRN.length) {
            return aIntGRN;
        } else {
            if (aIntGRN[startIdx] == 0) {
                return getBasicGRN(aIntGRN, startIdx+1);
            }
        }
        SimpleMaterial aMaterial = new SimpleMaterial(GeneralMethods.convertArrayToList(aIntGRN));

        final int[] target1 = {
                1, -1, 1, -1, 1,
                -1, 1, -1, 1, -1
        };
        final int[] target2 = {
                1, -1, 1, -1, 1,
                1, -1, 1, -1, 1
        };

        final int maxCycle = 30;

        final double perturbationRate = 0.15;
        final List<Integer> thresholds = Arrays.asList(0, 500);
        final int[] perturbationSizes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final double stride = 0.00;
        int[][] targets = {target1, target2};

        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

        double originalFitness = ((GRNFitnessFunctionMultipleTargets) fitnessFunction).evaluate(aMaterial, 501);

        int[] newIntGRN = aIntGRN.clone();
        newIntGRN[startIdx] = 0;

        SimpleMaterial aNewMaterial = new SimpleMaterial(GeneralMethods.convertArrayToList(newIntGRN));
        double newFitness = ((GRNFitnessFunctionMultipleTargets) fitnessFunction).evaluate(aNewMaterial, 501);

        if (newFitness >= originalFitness) {
            return getBasicGRN(newIntGRN, startIdx+1);
        } else {
            return getBasicGRN(aIntGRN, startIdx+1);
        }
    }

    public static void main(String[] args) {
        String fileName = "/Users/qin/Portal/generated-outputs/fixed-record-zhenyue-balanced-combinations-elite-p001/volcanoe_grns.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String aStrGRN = line.replace("[", "").replace("]", "").replace(" ", "");
                int[] aIntGRN = Arrays.stream(aStrGRN.split(",")).mapToInt(Integer::parseInt).toArray();
                int[] rtn = getBasicGRN(aIntGRN, 0);

                final int[] target1 = {
                        1, -1, 1, -1, 1,
                        -1, 1, -1, 1, -1
                };
                final int[] target2 = {
                        1, -1, 1, -1, 1,
                        1, -1, 1, -1, 1
                };

                final int maxCycle = 30;

                final double perturbationRate = 0.15;
                final List<Integer> thresholds = Arrays.asList(0, 500);
                final int[] perturbationSizes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                final double stride = 0.00;
                int[][] targets = {target1, target2};

                FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                        targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

                SimpleMaterial aMaterial = new SimpleMaterial(GeneralMethods.convertArrayToList(rtn));
                double originalFitness = ((GRNFitnessFunctionMultipleTargets) fitnessFunction).evaluate(aMaterial, 501);
                System.out.println("return fitness: " + originalFitness);


//                System.out.println(Arrays.toString(rtn));
//                System.out.println("Activation edge: " + GeneralMethods.getCertainEdgeNumber(rtn, 1) +
//                        " Depression edge: " + GeneralMethods.getCertainEdgeNumber(rtn, 1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
