package ga.components.hotspots;

import java.util.Map;
import java.util.Random;

/**
 * Created by Zhenyue Qin (秦震岳) on 4/7/17.
 * The Australian National University.
 */
public class MatrixHotspot extends Hotspot {
    private final int matrixSideSize;
    public MatrixHotspot(final int dnaLength) {
        super(dnaLength);
        this.matrixSideSize = (int) Math.sqrt(dnaLength);
        checkMatrixValidity();
    }

    public MatrixHotspot(final int size, final int dnaLength) {
        super(size, dnaLength);
        this.matrixSideSize = (int) Math.sqrt(dnaLength);
        checkMatrixValidity();
    }

    public MatrixHotspot(final int size, final int dnaLength, Map<Integer, Double> recombinationRates) {
        super(size, dnaLength, recombinationRates);
        this.matrixSideSize = (int) Math.sqrt(dnaLength);
        checkMatrixValidity();
    }

    private void checkMatrixValidity() {
        if (this.matrixSideSize * this.matrixSideSize != this.dnaLength) {
            throw new IllegalArgumentException("Hotspot dna length has to be a square number");
        }
    }

    @Override
    protected void generateRandomRecombinationRates() {
        generateRandomRecombinationRatesWithSize((int) Math.sqrt(dnaLength));
    }
}
