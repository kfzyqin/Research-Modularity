package genderGAWithHotspots.operations.hotspotMutators;

import com.sun.istack.internal.NotNull;
import genderGAWithHotspots.components.hotspots.DiscreteExpHotspot;
import genderGAWithHotspots.components.hotspots.Hotspot;

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
 * This is a hotspot mutation operation for DiscreteExpHotspot. Each time when mutation occurs the
 * value of the encoding will go up or down by at most 1.
 *
 * @author Siu Kei Muk (David)
 * @since 28/09/16.
 */
public class SimpleDiscreteExpHotspotMutator implements HotspotMutator<Integer> {

    private double control;
    private double promoteProbability = 0.5;

    public SimpleDiscreteExpHotspotMutator(final double control) {
        setControl(control);
    }

    public SimpleDiscreteExpHotspotMutator(final double control, final double promoteProbability) {
        this(control);
        setPromoteProbability(promoteProbability);
    }


    private void filter(final double control) {
        if (control < -1) throw new IllegalArgumentException("Control parameter must be at least -1.");
    }

    @Override
    public void mutate(@NotNull final Hotspot<Integer> hotspotMap) {
        DiscreteExpHotspot hotspot = (DiscreteExpHotspot) hotspotMap;
        final int maxLevel = hotspot.getMaxLevel();
        List<Integer> encoding = hotspot.getEncoding();
        int value;
        for (int i = 0; i < encoding.size(); i++) {
            value = encoding.get(i);
            if (Math.random() < Math.exp(-value - control)) {
                if (value == 1) encoding.set(i, 2);
                else if (value == maxLevel) encoding.set(i, maxLevel-1);
                else encoding.set(i, (Math.random() < promoteProbability) ? value+1 : value-1);
            }
        }
    }

    public double getControl() {
        return control;
    }

    public void setControl(final double control) {
        filter(control);
        this.control = control;
    }

    public double getPromoteProbability() {
        return promoteProbability;
    }

    public void setPromoteProbability(final double promoteProbability) {
        if (promoteProbability > 1 || promoteProbability < 0) throw new IllegalArgumentException("Invalid probability value.");
        this.promoteProbability = promoteProbability;
    }
}
