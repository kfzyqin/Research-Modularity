package experiments.experiment6;

import ga.others.GeneralMethods;

public class TestField14 {
    public static void main(String[] args) {
        Integer[] indices = new Integer[10];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }

        System.out.println(GeneralMethods.getCombination(indices, 1));

    }
}
