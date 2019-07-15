package tools;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.others.GeneralMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertionDeletionTool {
    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    private static final int maxCycle = 30;
    private static final int perturbations = 500;
    private static final double perturbationRate = 0.15;
    private static final int[] perturbationSizes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final List<Integer> thresholds = Arrays.asList(0, 500);
    private static final double stride = 0.00;
    static int[][] targets = {target1, target2};

    static GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
            targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

    private static List<Integer> getCertainEdgeIdxes(int[] aGRN, String edgeType) {
        List<Integer> rtn = new ArrayList<>();
        int dealType = -10;
        if (edgeType.equals("empty")) {
            dealType = 0;
        } else if (edgeType.equals("edge")) {
            dealType = 1;
        } else if (edgeType.equals("empty inter-mod")) {
            dealType = 2;
        } else if (edgeType.equals("inter-mod")) {
            dealType = 3;
        }
        for (int i=0; i<aGRN.length; i++) {
            if (dealType == 0) {
                if (aGRN[i] == 0) {
                    rtn.add(i);
                }
            } else if (dealType == 1) {
                if (aGRN[i] == 1 || aGRN[i] == -1) {
                    rtn.add(i);
                }
            } else if (dealType == 2) {
                rtn = GeneralMethods.getInterModuleEdgeIdxes(aGRN);
            } else if (dealType == 3) {
                rtn = GeneralMethods.getInterModuleNoEdgeIdxes(aGRN);
            }
        }
        return rtn;
    }

    private static List<Double> getEdgeInsertionFit(int[] aGRN, int dealNum, String edgeType) {
        List<Double> fits = new ArrayList<>();
        List<Integer> listIdxes = getCertainEdgeIdxes(aGRN, edgeType);
        Integer[] idxes = GeneralMethods.convertIntegerListToIntegerArray(listIdxes);
        List<List<Integer>> targetIdxes = GeneralMethods.getCombination(idxes, dealNum);
        for (List<Integer> aTargetIdxes : targetIdxes) {
            int[] aGRNCopy1 = aGRN.clone();
            for (int anIdx : aTargetIdxes) {
                if (Math.random() < 0.5) {
                    aGRNCopy1[anIdx] = 1;
                } else {
                    aGRNCopy1[anIdx] = -1;
                }
            }
            SimpleMaterial aMaterial = GeneralMethods.convertIntArrayToSimpleMaterial(aGRNCopy1);
            fits.add(fitnessFunction.evaluate(aMaterial, 502));
        }
        return fits;
    }

    private static List<Double> getEdgeDeletionFit(int[] aGRN, int dealNum, String edgeType) {
        List<Double> fits = new ArrayList<>();
        List<Integer> listIdxes = getCertainEdgeIdxes(aGRN, edgeType);
        Integer[] idxes = GeneralMethods.convertIntegerListToIntegerArray(listIdxes);
        List<List<Integer>> targetIdxes = GeneralMethods.getCombination(idxes, dealNum);
        for (List<Integer> aTargetIdxes : targetIdxes) {
            int[] aGRNCopy1 = aGRN.clone();
            for (int anIdx : aTargetIdxes) {
                aGRNCopy1[anIdx] = 0;
            }
            SimpleMaterial aMaterial = GeneralMethods.convertIntArrayToSimpleMaterial(aGRNCopy1);
            fits.add(fitnessFunction.evaluate(aMaterial, 502));
        }
        return fits;
    }

    public static void main(String[] args) {
        int[] targetGRN = {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, -1, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0};
        SimpleMaterial aMaterial = GeneralMethods.convertIntArrayToSimpleMaterial(targetGRN);

        Integer[] anArray = {1, 2, 3, 4, 5};

        System.out.println(GeneralMethods.getCombination(anArray, 3));

        List<Double> avgFits = new ArrayList<>();
        int dealNum = 2;
        System.out.println("deal num: " + dealNum);
        for (int i=0; i<3; i++) {
            List<Double> insertionFits = getEdgeInsertionFit(targetGRN, dealNum, "empty");
            avgFits.add(GeneralMethods.getAverageNumber(insertionFits));
        }
        System.out.println("Avg Insertion Fit: " + GeneralMethods.getAverageNumber(avgFits));

        List<Double> deletionFits = getEdgeDeletionFit(targetGRN, dealNum, "edge");
        System.out.println("Avg Deletion Fit: " + GeneralMethods.getAverageNumber(deletionFits));
    }
}
