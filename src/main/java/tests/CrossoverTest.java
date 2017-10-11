package tests;

/**
 * Created by Zhenyue Qin (秦震岳) on 8/7/17.
 * The Australian National University.
 */
public class CrossoverTest {
    public static void main(String[] args) {
        final int crossIndex = 5;
        final int matrixSideSize = 10;
        for (int currentCrossIndex=0; currentCrossIndex<crossIndex; currentCrossIndex++) {
            int tmpCrossIndex = currentCrossIndex;
            while (tmpCrossIndex < crossIndex * matrixSideSize) {
                System.out.println(tmpCrossIndex);
                tmpCrossIndex += matrixSideSize;
            }
        }

        System.out.println("=====");

        for (int currentCrossIndex=0; currentCrossIndex<crossIndex; currentCrossIndex++) {
            int tmpCrossIndex = currentCrossIndex;
            while (tmpCrossIndex < crossIndex * matrixSideSize) {
                System.out.println(tmpCrossIndex);
                tmpCrossIndex += matrixSideSize;
            }
        }

        System.out.println("=====");
        for (int currentCrossIndex=0; currentCrossIndex<crossIndex; currentCrossIndex++) {
            int tmpCrossIndex = currentCrossIndex;
            while (tmpCrossIndex < crossIndex * matrixSideSize) {
                System.out.println(tmpCrossIndex);
                tmpCrossIndex += matrixSideSize;
            }
        }

        for (int currentCrossIndex=crossIndex; currentCrossIndex<matrixSideSize; currentCrossIndex++) {
            int tmpCrossIndex = currentCrossIndex + crossIndex * matrixSideSize;
            while (tmpCrossIndex < matrixSideSize * matrixSideSize) {
                System.out.println(tmpCrossIndex);
                tmpCrossIndex += matrixSideSize;
            }
        }
    }
}
