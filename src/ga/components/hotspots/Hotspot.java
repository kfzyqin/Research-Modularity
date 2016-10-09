package ga.components.hotspots;

import com.sun.istack.internal.NotNull;
import ga.others.Copyable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by david on 28/09/16.
 */
public abstract class Hotspot<H> implements Copyable<Hotspot<H>>{

    protected final int size;
    protected List<H> encoding;
    protected List<Double> recombinationRate;
    protected boolean modified;

    public Hotspot(final int size) {
        this.size = size;
        encoding = new ArrayList<>(size);
        recombinationRate = new ArrayList<>(size);
        modified = true;
        // initialize();
    }

    protected Hotspot(final int size, @NotNull List<H> encoding, @NotNull List<Double> rate) {
        this.size = size;
        this.encoding = encoding;
        this.recombinationRate = rate;
        modified = false;
    }

    public abstract void setEncodingValue(final int index, @NotNull final H value);
    protected abstract void initialize();
    protected abstract void computeRate();

    public List<Double> getRecombinationRate() {
        if (modified) computeRate();
        return Collections.unmodifiableList(recombinationRate);
    }

    public List<H> getEncoding() {
        return encoding;
    }
}
