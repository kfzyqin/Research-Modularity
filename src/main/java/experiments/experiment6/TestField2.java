package experiments.experiment6;

import ga.others.GeneralMethods;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhenyueqin on 24/6/17.
 */
public class TestField2 {
    public static final int[] target3 = {-1, 1, -1, 1};

    public static void main(String[] args) {
        List<Integer> aList = new ArrayList<>();
        aList.add(1);
        aList.add(-1);
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(-1);
        System.out.println(GeneralMethods.getAllLists(set, 2, 2));
    }
}
