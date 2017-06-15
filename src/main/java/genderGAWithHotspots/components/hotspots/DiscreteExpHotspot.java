package genderGAWithHotspots.components.hotspots;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
 * This class provides an implementation for Hotspot with rates following the exponential distribution
 * where the exponents are discrete values. r = exp(-control + (-maxlevel + encoding))
 *
 * @author Siu Kei Muk (David)
 * @since 28/09/16.
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
