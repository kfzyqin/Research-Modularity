package tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Randomness {
    public static List<Integer> generateASetOfIntegers(int lowerBound, int upperBound, int size) {
        List<Integer> rtn = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = lowerBound; i < upperBound+1; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < size; i++) {
            rtn.add(list.get(i));
        }
        return rtn;
    }
}
