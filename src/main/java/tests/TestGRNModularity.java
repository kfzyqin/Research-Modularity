package tests;

import ga.others.GeneralMethods;
import tools.GRNModularity;
import tools.Randomness;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TestGRNModularity {
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

        List<Double> mods = new ArrayList<>();
        int[] ses = {20, 21, 22, 23, 24};
        for (int aSz : ses) {
            for (int i = 0; i < 1000; i++) {
                List<Integer> edgeIdxes = Randomness.generateASetOfIntegers(0, 99, aSz);
                List<Integer> aGRN = new ArrayList<>(Collections.nCopies(100, 0));
                for (Integer edgeIdx : edgeIdxes) {
                    aGRN.set(edgeIdx, rd.nextFloat() < 0.5 ? -1 : 1);
                }
                mods.add(GRNModularity.getGRNModularity(aGRN));
            }
        }
        System.out.println(GeneralMethods.getAverageNumber(mods));
        System.out.println(Collections.max(mods));
        System.out.println(Collections.min(mods));
    }
}
