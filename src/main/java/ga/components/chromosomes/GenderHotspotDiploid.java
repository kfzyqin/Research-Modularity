package ga.components.chromosomes;

import com.sun.istack.internal.NotNull;
import ga.components.hotspots.Hotspot;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

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
 * This class is a Coupleable extension of SimpleDiploid.
 *
 * @author Siu Kei Muk (David) and Zhenyue Qin
 * @since 27/08/16.
 */
public class GenderHotspotDiploid extends GenderDiploid implements CoupleableWithHotspot {

    protected Hotspot hotspot;

    public GenderHotspotDiploid(@NotNull final SimpleMaterial dna1,
                                @NotNull final SimpleMaterial dna2,
                                @NotNull final ExpressionMap<SimpleMaterial, SimpleMaterial> mapping,
                                @NotNull final Hotspot hotspot,
                                final boolean masculine) {

        super(dna1, dna2, mapping, masculine);
        this.hotspot = hotspot;
    }

    @Override
    public boolean isMasculine(){
        return masculine;
    }

    public Hotspot getHotspot() {
        return hotspot;
    }

    @Override
    public GenderHotspotDiploid copy() {
        SimpleMaterial dna1 = genotype.get(0).copy();
        SimpleMaterial dna2 = genotype.get(1).copy();
        ExpressionMap mapping = super.mapping.copy();
        Hotspot hotspotCopy = hotspot.copy();
        return new GenderHotspotDiploid(dna1, dna2, mapping, hotspotCopy, masculine);
    }

    @Override
    public String toString() {
        List<Double> map = hotspot.getRecombinationRates();
        return super.toString() + ", Recombination probability: " + map.toString();
    }

     /*
     Todo: make this inheritance from CoupleableWithHotspot work
      */
    public Hotspot getHotSpot(){
        return this.hotspot;
    }
}
