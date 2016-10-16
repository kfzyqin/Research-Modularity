package genderGAWithHotspots.components.hotspots;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 28/09/16.
 */
public class SimpleHotspot extends Hotspot<Double> {

    public SimpleHotspot(final int size) {
        super(size);
        initialize();
    }

    protected SimpleHotspot(final int size, List<Double> encoding) {
        super(size, encoding, encoding);
    }

    @Override
    protected void initialize() {
        for (int i = 0; i < size; i++) encoding.add(Math.random());
    }

    @Override
    protected void computeRate() {
        if (recombinationRate.size() == size)
            for (int i = 0; i < size; i++) recombinationRate.set(i, encoding.get(i));
        else {
            for (int i = 0; i < size; i++) {
                recombinationRate.clear();
                recombinationRate.add(encoding.get(i));
            }
        }
    }

    @Override
    public void setEncodingValue(final int index, @NotNull final Double value) {
        if (index < 0 || index > size-1) return;
        if (value < 0 || value > 1) return;
        encoding.set(index, value);
        modified = true;
    }

    @Override
    public SimpleHotspot copy() {
        List<Double> encodingCopy = new ArrayList<>(encoding);
        SimpleHotspot copy = new SimpleHotspot(size, encodingCopy);
        return copy;
    }
}
