package genderGAWithHotspots.components.hotspots;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Zhenyue Qin on 26/04/2017.
 * The Australian National University.
 */
public class UniformHotspot extends Hotspot<Integer> {
    public UniformHotspot(int size) {
        super(size);
        initialize();
    }

    public UniformHotspot(int size,
                          @NotNull final List<Integer> encoding,
                          @NotNull final List<Double> rate) {
        super(size, encoding, rate);
        initialize();
    }

    @Override
    public Hotspot<Integer> copy() {
        return new UniformHotspot(this.size, this.encoding, this.recombinationRate);
    }

    @Override
    public void setEncodingValue(int index, Integer value) {

    }

    @Override
    protected void initialize() {
        for (int i = 0; i < size; i++) encoding.add(i);
        computeRate();
    }

  @Override
  protected void computeRate() {
      if (recombinationRate.size() == size)
          for (int i = 0; i < size; i++) recombinationRate.set(i, 1.0/(double) size);
      else {
          recombinationRate.clear();
          for (int i = 0; i < size; i++) {
              recombinationRate.add(0/(double) size);
          }
      }
      modified = false;
  }
}
