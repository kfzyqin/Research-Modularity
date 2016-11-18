package genderGAWithHotspots.components.hotspots;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
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
 * This class provides a simple implementation of hotspot with identity mapping. The encoding is the
 * recombination rate itself.
 *
 * @author Siu Kei Muk (David)
 * @since 28/09/16.
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
