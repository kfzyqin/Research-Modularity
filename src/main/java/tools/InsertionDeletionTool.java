package tools;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.operations.mutators.GRNEdgeMutator;
import ga.others.GeneralMethods;

import java.io.File;
import java.util.*;

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
        if (edgeType.equals("insert")) {
            dealType = 0;
        } else if (edgeType.equals("delete")) {
            dealType = 1;
        } else if (edgeType.equals("insert inter-mod")) {
            dealType = 2;
        } else if (edgeType.equals("delete inter-mod")) {
            dealType = 3;
        } else if (edgeType.equals("insert intra-mod")) {
            dealType = 4;
        } else if (edgeType.equals("delete intra-mod")) {
            dealType = 5;
        }
        if (dealType == 0) {
            for (int i=0; i<aGRN.length; i++) {
                if (aGRN[i] == 0) {
                    rtn.add(i);
                }
            }
        } else if (dealType == 1) {
            for (int i = 0; i < aGRN.length; i++) {
                if (aGRN[i] == 1 || aGRN[i] == -1) {
                    rtn.add(i);
                }
            }
        } else if (dealType == 2) {
            rtn = GeneralMethods.getInterModuleNoEdgeIdxes(aGRN);
        } else if (dealType == 3) {
            rtn = GeneralMethods.getInterModuleEdgeIdxes(aGRN);
        } else if (dealType == 4) {
            rtn = GeneralMethods.getIntraModuleNoEdgeIdxes(aGRN);
        } else if (dealType == 5) {
            rtn = GeneralMethods.getIntraModuleEdgeIdxes(aGRN);
        }
        return rtn;
    }

    private static List<Double> getEdgeInsertionFit(int[] aGRN, int dealNum, String edgeType, int gen) {
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
            fits.add(fitnessFunction.evaluate(aMaterial, gen));
        }
        return fits;
    }

    private static List<Double> getEdgeDeletionFit(int[] aGRN, int dealNum, String edgeType, int gen) {
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
            fits.add(fitnessFunction.evaluate(aMaterial, gen));
        }
        return fits;
    }

    public static List<int[]> getGRNsOfAGeneration(String targetPath, int aGen) {
        String targetFormat = targetPath + "/population-phenotypes/all-population-phenotype_gen_%d.lists";
        String targetFormatted = String.format(targetFormat, aGen);
        List<String[]> lines = GeneralMethods.readFileLineByLine(targetFormatted);
        List<int[]> rtn = new ArrayList<>();
        for (int i=0; i<lines.size(); i++) {
            SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lines.get(i));
            rtn.add(GeneralMethods.convertSimpleMaterialToIntArray(aMaterial));
        }
        return rtn;
    }

    public static void printAIntIdxedHashMap(Map<Integer, ?> aMap) {
        List<Integer> sortedList = new ArrayList<>(aMap.keySet());
        Collections.sort(sortedList);
        for (Integer e : sortedList) {
            System.out.println("Edge: " + e + "\t" + "Fitness: " + aMap.get(e));
        }
    }

    private static double mutationRate = 0.05;
    public static void main(String[] args) {
        String dirPath = "/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments/distributional-tournament-no-x";
        File[] dirPathList = new File(dirPath).listFiles(File::isDirectory);
        GRNEdgeMutator edgeGeneMutator = new GRNEdgeMutator(mutationRate);

        Map<Integer, Double> edgeNumAvgDelFitMap = new HashMap<>();
        int targetGen = 499;
        for (int dirIdx=0; dirIdx<30; dirIdx++) {
            File aDirPath = dirPathList[dirIdx];
            List<int[]> generationGRNs = getGRNsOfAGeneration(aDirPath.toString(), targetGen);
            int[] aGenerationGRN = generationGRNs.get(0);
            int[] targetGRN = aGenerationGRN;

            SimpleMaterial aMaterial = GeneralMethods.convertIntArrayToSimpleMaterial(targetGRN);
            GeneralMethods.printSquareGRN(targetGRN);

            double origFit = fitnessFunction.evaluate(aMaterial, targetGen);
            System.out.println("Original fitness: " + origFit);
            int origEdgeNum = GeneralMethods.getEdgeNumber(aMaterial);
            System.out.println("Original edge number: " + origEdgeNum);

            SimpleMaterial postMutationMaterial = aMaterial.copy();
            edgeGeneMutator.mutateAGRN(postMutationMaterial);
            double postMutFit = fitnessFunction.evaluate(postMutationMaterial);
//            System.out.println("Post mutation fitness: " + postMutFit);

            List<Double> avgInsertionFits = new ArrayList<>();
            int dealNum = 1;
            System.out.println("deal num: " + dealNum);
            for (int i=0; i<5; i++) {
                List<Double> insertionFits = getEdgeInsertionFit(targetGRN, dealNum, "insert", targetGen);
                avgInsertionFits.add(GeneralMethods.getAverageNumber(insertionFits));
            }
            double avgInsertionFit = GeneralMethods.getAverageNumber(avgInsertionFits);
            System.out.println("Avg Insertion Fit: " + avgInsertionFit);

            List<Double> deletionFits = getEdgeDeletionFit(targetGRN, dealNum, "delete", targetGen);
            double avgDeletionFit = GeneralMethods.getAverageNumber(deletionFits);
            System.out.println("Avg Deletion Fit: " + avgDeletionFit);

            if (origEdgeNum > 20) {
                edgeNumAvgDelFitMap.put(origEdgeNum, avgDeletionFit);
            } else {
                edgeNumAvgDelFitMap.put(origEdgeNum, avgInsertionFit);
            }

            System.out.println("Avg insertion deletion fit: " + (avgInsertionFit + avgDeletionFit) / 2.0);

            List<Double> avgInterModInsertionFits = new ArrayList<>();
            for (int i=0; i<3; i++) {
                List<Double> insertionFits = getEdgeInsertionFit(targetGRN, dealNum, "insert inter-mod", targetGen);
                avgInterModInsertionFits.add(GeneralMethods.getAverageNumber(insertionFits));
            }
            System.out.println("Avg Inter Module Insertion Fit: " + GeneralMethods.getAverageNumber(avgInterModInsertionFits));

            List<Double> deletionInterModFits = getEdgeDeletionFit(targetGRN, dealNum, "delete inter-mod", targetGen);
            System.out.println("Avg Inter Module Deletion Fit: " + GeneralMethods.getAverageNumber(deletionInterModFits));

            List<Double> avgIntraModInsertionFits = new ArrayList<>();
            for (int i=0; i<3; i++) {
                List<Double> insertionFits = getEdgeInsertionFit(targetGRN, dealNum, "insert intra-mod", targetGen);
                avgIntraModInsertionFits.add(GeneralMethods.getAverageNumber(insertionFits));
            }
            System.out.println("Avg Intra Module Insertion Fit: " + GeneralMethods.getAverageNumber(avgIntraModInsertionFits));

            List<Double> deletionIntraModFits = getEdgeDeletionFit(targetGRN, dealNum, "delete intra-mod", targetGen);
            System.out.println("Avg Intra Module Deletion Fit: " + GeneralMethods.getAverageNumber(deletionIntraModFits));
        }
        printAIntIdxedHashMap(edgeNumAvgDelFitMap);
    }
}
