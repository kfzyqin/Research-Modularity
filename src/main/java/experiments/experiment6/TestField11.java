package experiments.experiment6;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Zhenyue Qin (秦震岳) on 8/7/17.
 * The Australian National University.
 */
public class TestField11 {
    public static void main(String[] args) {
        final int crossIndex = 5;
        final int matrixSideSize = 10;
        for (int currentCrossIndex=crossIndex*matrixSideSize; currentCrossIndex<matrixSideSize*matrixSideSize; currentCrossIndex+=matrixSideSize) {
            int tmpCrossIndex = currentCrossIndex;
            while (tmpCrossIndex < currentCrossIndex + matrixSideSize) {
                System.out.println(tmpCrossIndex);
                tmpCrossIndex += 1;
            }
        }
    }
}
