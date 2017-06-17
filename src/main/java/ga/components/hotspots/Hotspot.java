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
    protected final int dnaLength;
    protected Map<Integer, Double> recombinationRate;

    public Hotspot(final int size, final int dnaLength) {
        this.size = size;
        this.recombinationRate = new HashMap<>(this.size);
        this.dnaLength = dnaLength;
        this.generateRandomRecombinationRate();
    }

    public Hotspot(final int size, final int dnaLength, @NotNull Map<Integer, Double> recombinationRate) {
        this.size = size;
        this.dnaLength = dnaLength;
        this.recombinationRate = recombinationRate;
    }

    public void generateRandomRecombinationRate() {
        int[] hotspotPositions = new Random().ints(0, dnaLength).distinct().limit(size).toArray();
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

    /*
    Get the number of hotspot positions.
     */
    public int getSize() {
        return this.size;
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

    public Map<Integer, Double> getRecombinationRateMap() {
        return this.recombinationRate;
    }

    @Override
    public Hotspot copy() {
          return new Hotspot(this.size, this.dnaLength, this.recombinationRate);
    }

    public SortedSet<Integer> getSortedHotspotPositions() {
        SortedSet<Integer> positions = new TreeSet<Integer>(recombinationRate.keySet());
        return positions;
    }
}
