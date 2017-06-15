package ga.components.hotspots;

import com.sun.istack.internal.NotNull;
import ga.others.Copyable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhenyue Qin on 15/06/2017.
 * The Australian National University.
 */
public class Hotspot implements Copyable<Hotspot> {
    protected final int size;
    protected Map<Integer, Double> recombinationRate;
    protected final double mutationRate;

    public Hotspot(final int size, final double mutationRate) {
        this.size = size;
        this.recombinationRate = new HashMap<>(this.size);
        this.mutationRate = mutationRate;
    }

    public Hotspot(final int size, @NotNull Map<Integer, Double> recombinationRate, double mutationRate) {
        this.size = size;
        this.recombinationRate = recombinationRate;
        this.mutationRate = mutationRate;
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
      return new Hotspot(this.size, this.recombinationRate, this.mutationRate);
  }
}
