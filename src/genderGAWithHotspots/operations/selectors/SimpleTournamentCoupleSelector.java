package genderGAWithHotspots.operations.selectors;

import ga.components.chromosomes.Chromosome;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import genderGAWithHotspots.components.chromosomes.Coupleable;

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
 *
 *
 * Created by david on 29/09/16.
 */
public class SimpleTournamentCoupleSelector<G extends Chromosome & Coupleable> extends CoupleSelector<G>{

    public SimpleTournamentCoupleSelector(final int size, final double dominanceProbability) {
        super(new SimpleTournamentScheme(size, dominanceProbability));
    }
}
