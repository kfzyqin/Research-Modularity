package experiments.experiment6;

import ga.components.genes.EdgeGene;
import ga.components.materials.GRN;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFast;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsFastHidden;
import ga.others.GeneralMethods;

import java.util.*;

/**
 * Test the maximum potential of GRNs for multiple targets
 * Created by zhenyueqin on 27/6/17.
 */
public class TestField6 {
    public static final int[] target1 = {1, -1, 1};
    public static final int[] target2 = {1, -1, -1};

    public static void main(String[] args) {
        Integer[] values = {0, 300};
        List<Integer> tmpThresholds = new ArrayList<>(Arrays.asList(values));

        Set<Double> fitnessSet = new TreeSet<>();
        Map<Double, GRN> fitnessMap = new TreeMap();

        GRNFitnessFunctionMultipleTargetsFast grnFit1 = new GRNFitnessFunctionMultipleTargetsFast(
                target1, target2, 100, 300, 0.15, tmpThresholds, 100);

        GRNFitnessFunctionMultipleTargetsFastHidden grnFit2 = new GRNFitnessFunctionMultipleTargetsFastHidden(
                target1, target2, 100, 300, 0.15, tmpThresholds, 100, 0);

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(0);
        set.add(-1);
        Set<List<Integer>> perturbations = GeneralMethods.getAllLists(set, 9, 9);
        int counter = 0;
        for (List<Integer> aList : perturbations) {
            GRN grn = new GRN(convertIntegerListToEdgeGeneList(aList));
            double aFitness = grnFit2.evaluate(grn, 400);
            if (counter % 1000 == 0) {
                System.out.println("progress: " + counter + ", sum: " + perturbations.size());
            }
            if (aFitness > 0.95) {
                System.out.println("watch out!");
                System.out.println(aFitness);
            }
            fitnessMap.put(aFitness, grn);
            counter += 1;
        }

        System.out.println(Collections.max(fitnessMap.keySet()));
        System.out.println(fitnessMap.get(Collections.max(fitnessMap.keySet())));
    }

    public static List<EdgeGene> convertIntegerListToEdgeGeneList(List<Integer> aList) {
        List<EdgeGene> rtn = new ArrayList<>();
        for (Integer e : aList) {
            rtn.add(new EdgeGene(e));
        }
        return rtn;
    }
}
