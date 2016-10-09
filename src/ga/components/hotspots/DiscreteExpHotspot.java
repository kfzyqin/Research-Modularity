package ga.components.hotspots;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 28/09/16.
 */
public class DiscreteExpHotspot extends Hotspot<Integer>{

    private final int maxLevel;
    private double control;

    public DiscreteExpHotspot(final int size, final int maxLevel, final double control) {
        super(size);
        if (maxLevel < 1) throw new IllegalArgumentException("Maximum level must be at least 1.");
        this.maxLevel = maxLevel;
        setControl(control);
        initialize();
    }

    private DiscreteExpHotspot(final int size, final int maxLevel,
                               @NotNull final List<Integer> encoding,
                               @NotNull final List<Double> rate) {
        super(size, encoding,rate);
        this.maxLevel = maxLevel;
    }

    private void filter(final double control) {
        if (control < 0) throw new IllegalArgumentException("Control parameter must be non-negative.");
    }

    @Override
    public DiscreteExpHotspot copy() {
        List<Integer> encodingCopy = new ArrayList<>(encoding);
        List<Double> rateCopy = new ArrayList<>(recombinationRate);
        return new DiscreteExpHotspot(size, maxLevel, encodingCopy, rateCopy);
    }

    @Override
    protected void initialize() {
        for (int i = 0; i < size; i++) encoding.add(ThreadLocalRandom.current().nextInt(1,maxLevel+1));
        computeRate();
    }

    @Override
    protected void computeRate() {
        if (recombinationRate.size() == size)
            for (int i = 0; i < size; i++) recombinationRate.set(i, Math.exp(-encoding.get(i) - control));
        else {
            recombinationRate.clear();
            for (int i = 0; i < size; i++) {
                recombinationRate.add(Math.exp(encoding.get(i) - maxLevel - control));
            }
        }
        modified = false;
    }

    @Override
    public void setEncodingValue(final int index, @NotNull Integer value) {
        if (index > size-1 || size < 0) return;
        if (value < 1) value = (value % maxLevel) + maxLevel;
        if (value > maxLevel) value %= maxLevel;
        encoding.set(index, value);
        modified = true;
    }

    public double getControl() {
        return control;
    }

    public void setControl(double control) {
        filter(control);
        this.control = control;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
