package tests;

import tools.GRNModularity;
import tools.ListTools;

import java.util.Arrays;
import java.util.List;

public class RandomTesting {

    public static void main(String[] args) {
        List<Double> test = Arrays.asList(1.0, 2.0, 9.0, 4.0, 5.0, 3.0, 2.0, 1.0);
        System.out.println(ListTools.getArgMax(test));
    }
}
