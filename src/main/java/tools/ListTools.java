package tools;

import java.util.Collections;
import java.util.List;

public class ListTools {
    public static int getArgMax(List<Double> aList) {
        double actualMax = Collections.max(aList);
        for (int i=0; i<aList.size(); i++) {
            if (aList.get(i) == actualMax) {
                return i;
            }
        }
        throw new RuntimeException("Arg max is wrong. ");
    }

    public static double getListAvg(List<Double> aList) {
        double aSum = 0;
        for (Double e : aList) {
            aSum += e;
        }
        return aSum / aList.size();
    }
}
