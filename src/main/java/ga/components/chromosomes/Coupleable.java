package ga.components.chromosomes;

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

import ga.components.hotspots.Hotspot;

/**
 * This interface requires a Chromosome to have a gender and contain a Hotspot object that guides
 * the recombination process in the reproduction.
 *
 * @author Siu Kei Muk (David) and Zhenyue Qin
 * @since 29/09/16.
 */
public interface Coupleable {
    boolean isMasculine();
}
