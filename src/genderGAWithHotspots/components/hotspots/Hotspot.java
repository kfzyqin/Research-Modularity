package genderGAWithHotspots.components.hotspots;

import com.sun.istack.internal.NotNull;
import ga.others.Copyable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    GASEE is a Java-based genetic algorithm library for scientific exploration and experiment.
    Copyright 2016 Siu-Kei Muk

    This file is part of GASEE.

    GASEE is free library: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 2.1 of the License, or
    (at your option) any later version.

    GASEE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with GASEE.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * The Hotspot is separated into two parts: encoding and actual recombination rate. The
 * recombination rate defines the likelihood of occurrence of recombination at the corresponding
 * gene position in the genotype. The encoding allow an underlying mechanism that governs the
 * rate to be implemented.
 *
 * @param <H> The type of encoding used in the hotspot.
 *
 * @author Siu Kei Muk (David)
 * @since 28/09/16.
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
    }

    protected Hotspot(final int size, @NotNull List<H> encoding, @NotNull List<Double> rate) {
        this.size = size;
        this.encoding = encoding;
        this.recombinationRate = rate;
        modified = false;
    }

    /**
     *
     * @param index index of encoding to be set
     * @param value
     */
    public abstract void setEncodingValue(final int index, @NotNull final H value);

    /**
     * This method is for initialization of a hotspot. One can initialize the hotspot directly
     * in the constructor without calling it. However, it is recommended to implement this method
     * and call it from the constructor for readability.
     */
    protected abstract void initialize();

    /**
     * This method maps the current encoding of the hotspot into recombination rate.
     */
    protected abstract void computeRate();

    public List<Double> getRecombinationRate() {
        if (modified) computeRate();
        return Collections.unmodifiableList(recombinationRate);
    }

    public List<H> getEncoding() {
        return encoding;
    }
}
