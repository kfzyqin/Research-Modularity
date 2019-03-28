package experiments.experiment6;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsBalanceAsymmetric;
import ga.others.GeneralMethods;
import ga.others.ModularityPathAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ESWModularityRepeater {
    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    private static final int maxCycle = 100;

    private static final double perturbationRate = 0.15;
    private static final List<Integer> thresholds = Arrays.asList(0, 500);
    private static final int[] perturbationSizes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final double stride = 0.00;



    public static void main(String[] args) {
        int[][] targets = {target1, target2};

        FitnessFunction fitnessFunctionESW = new GRNFitnessFunctionMultipleTargetsBalanceAsymmetric(
                targets, maxCycle, 500, perturbationRate, thresholds, stride);

        SimpleMaterial material1 = new SimpleMaterial(GeneralMethods.convertArrayToList(WarehouseGRN.com_samp_g_opt_grn_1));
        SimpleMaterial material2 = new SimpleMaterial(GeneralMethods.convertArrayToList(WarehouseGRN.esw_g_opt_grn_1));

        int fitnessNo = 100;
        int trialNo = 100;

        List<Double> com_sam_trial_avg = new ArrayList<>();
        List<Double> esw_trial_avg = new ArrayList<>();

        for (int j=0; j<trialNo; j++) {
            List<Double> com_sam_fits = new ArrayList<>();
            List<Double> esw_fits = new ArrayList<>();
            for (int i = 0; i < fitnessNo; i++) {
                List<Double> material1Fit = ModularityPathAnalyzer.removeEdgeAnalyzer(0, material1,
                        fitnessFunctionESW, true, 1700, null, false);

                com_sam_fits.add(material1Fit.get(material1Fit.size() - 1));

                List<Double> material2Fit = ModularityPathAnalyzer.removeEdgeAnalyzer(0, material2,
                        fitnessFunctionESW, true, 1700, null, false);

                esw_fits.add(material2Fit.get(material2Fit.size() - 1));
            }
            com_sam_trial_avg.add(GeneralMethods.getAverageNumber(com_sam_fits));
            esw_trial_avg.add(GeneralMethods.getAverageNumber(esw_fits));
        }

        System.out.println(com_sam_trial_avg);
        System.out.println(esw_trial_avg);



    }
}
