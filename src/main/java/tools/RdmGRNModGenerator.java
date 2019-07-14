package tools;

import ga.others.GeneralMethods;
import tools.GRNModularity;
import tools.Randomness;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RdmGRNModGenerator {
    public static void main(String[] args) {
        Random rd = new Random();
//        List<Integer> aList = Arrays.asList(
//                0, 1, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 1, 0,
//                0, 0, 0, 1, 0, 0,
//                0, 0, 0, 0, 0, 0);
//        double mod = GRNModularity.getGRNModularity(aList);
//        System.out.println(mod);

        List<Double> avgMods = new ArrayList<>();
        List<Double> maxMods = new ArrayList<>();
        List<Double> minMods = new ArrayList<>();

        int[] ses = {15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50};
        for (int aSz : ses) {
            List<Double> mods = new ArrayList<>();
            for (int i = 0; i < 1000000; i++) {
                List<Integer> edgeIdxes = Randomness.generateASetOfIntegers(0, 99, aSz);
                List<Integer> aGRN = new ArrayList<>(Collections.nCopies(100, 0));
                for (Integer edgeIdx : edgeIdxes) {
                    aGRN.set(edgeIdx, rd.nextFloat() < 0.5 ? -1 : 1);
                }
                mods.add(GRNModularity.getGRNModularity(aGRN));
            }
            avgMods.add(GeneralMethods.getAverageNumber(mods));
            maxMods.add(Collections.max(mods));
            minMods.add(Collections.min(mods));

        }
        System.out.println(avgMods);
        System.out.println(maxMods);
        System.out.println(minMods);
    }
}
