package experiments.experiment6;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.others.GeneralMethods;

import java.util.Arrays;
import java.util.List;

public class SeparateModuleTester {
    static final int[] target1 = {
            1, -1, 1, -1, 1,
    };

    static final int[] target2 = {
            -1, 1, -1, 1, -1
    };

    public static void main(String[] args) {
        int[][] target_1_1 = {target1, target1};
        int[][] target_1_2 = {target1, target2};
        int[][] target_2_2 = {target2, target2};

//        int[] intGRN = {
//                1,  -1, 1,  -1, 1,
//                -1, 1,  -1, 1,  -1,
//                1,  -1, 1,  -1, 1,
//                -1, 1,  -1, 1,  -1,
//                1,  -1, 1,  -1, 1
//        };

        int[] intGRN = {
                0,	-1,	0,	-1,	0,
                -1,	1,	-1,	1,	0,
                1,	0,	1,	0,	1,
                -1,	0,	-1,	1,	0,
                1,	-1,	1,	0,	0,
        };


        final int maxCycle = 30;

        final double perturbationRate = 0.15;
        final List<Integer> thresholds = Arrays.asList(0, 500);
        final int[] perturbationSizes = {0, 1, 2, 3, 4, 5};
        final double stride = 0.00;
        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                target_1_2, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

        SimpleMaterial aNewMaterial = new SimpleMaterial(GeneralMethods.convertArrayToList(intGRN));
        double newFitness = ((GRNFitnessFunctionMultipleTargets) fitnessFunction).evaluate(aNewMaterial, 501);
        System.out.println("a fitness: " + newFitness);

    }
}
