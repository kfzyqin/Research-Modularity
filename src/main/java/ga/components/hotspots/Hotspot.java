package ga.components.hotspots;

import com.sun.istack.internal.NotNull;
import ga.others.Copyable;

import java.util.*;

/**
 * Created by Zhenyue Qin on 15/06/2017.
 * The Australian National University.
 */
public class Hotspot implements Copyable<Hotspot> {
    protected final int size;
    protected final int dnaLenght;
    protected Map<Integer, Double> recombinationRate;
    protected final double mutationRate;

    public Hotspot(final int size, final int dnaLength, final double mutationRate) {
        this.size = size;
        this.recombinationRate = new HashMap<>(this.size);
        this.dnaLenght = dnaLength;
        this.generateRandomRecombinationRate();
        this.mutationRate = mutationRate;
    }

    public Hotspot(final int size, final int dnaLength, @NotNull Map<Integer, Double> recombinationRate, double mutationRate) {
        this.size = size;
        this.dnaLenght = dnaLength;
        this.recombinationRate = recombinationRate;
        this.mutationRate = mutationRate;
    }

    public void generateRandomRecombinationRate() {
        int[] hotspotPositions = new Random().ints(0, dnaLenght).distinct().limit(size).toArray();
        double[] unNormalizedRates = new double[size];
        double rateSum = 0;
        for (int i=0; i<size; i++) {
            double aRate = Math.random();
            unNormalizedRates[i] = aRate;
            rateSum += aRate;
        }
        for (int i=0; i<size; i++) {
            setRecombinationRateAtPosition(hotspotPositions[i], unNormalizedRates[i] / rateSum);
        }
    }

    public void setRecombinationRateAtPosition(int i, double rate) {
        this.recombinationRate.put(i, rate);
    }

    public double getRecombinationRateAtPosition(int i) {
        return this.recombinationRate.get(i);
    }

    public List<Double> getRecombinationRates() {
        return new ArrayList<>(this.recombinationRate.values());
    }

    @Override
    public Hotspot copy() {
          return new Hotspot(this.size, this.dnaLenght, this.recombinationRate, this.mutationRate);
    }
}
