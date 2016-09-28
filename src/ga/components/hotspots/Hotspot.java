package ga.components.hotspots;

import com.sun.istack.internal.NotNull;
import ga.others.Copyable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by david on 28/09/16.
 */
public abstract class Hotspot<V> implements Copyable<Hotspot<V>>{

    protected final int size;
    protected List<V> encoding;
    protected List<Double> recombinationRate;
    protected boolean modified;

    public Hotspot(final int size) {
        this.size = size;
        encoding = new ArrayList<>(size);
        recombinationRate = new ArrayList<>(size);
        modified = true;
        initialize();
    }

    protected Hotspot(final int size, List<V> encoding) {
        this.size = size;
        this.encoding = encoding;

        modified = false;
    }

    public abstract void setEncodingValue(final int index, @NotNull final V value);
    protected abstract void initialize();
    protected abstract void computeRate();

    public List<Double> getRecombinationRate() {
        if (modified) computeRate();
        modified = false;
        return Collections.unmodifiableList(recombinationRate);
    }

    public List<V> getEncoding() {
        return encoding;
    }
}
