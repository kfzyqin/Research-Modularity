package tests;

import tools.GRNModularity;
import tools.QinModularity;

import java.util.Arrays;
import java.util.List;

public class TestGRNModularity {
    public static void main(String[] args) {
        List<Integer> aList = Arrays.asList(
                0, 1, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 1, 0,
                0, 0, 0, 1, 0, 0,
                0, 0, 0, 0, 0, 0);
        double mod = GRNModularity.getGRNModularity(aList);
        System.out.println(mod);
    }
}
