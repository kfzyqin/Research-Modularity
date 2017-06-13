package ga.operations.selectionOperators.selectionSchemes;

import com.sun.istack.internal.NotNull;

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
 * This class implements the proportionate selection scheme.
 * The given fitnessFunctions values are assumed to be normalized.
 *
 * @author Siu Kei Muk (David)
 * @since 9/09/16.
 */
public class ProportionalScheme implements SelectionScheme{

    /**
     * @param fitnessValues descending sorted normalized (range from 0 to 1, sum to 1) fitnessFunctions values of individuals.
     * @return index of selected individual
     */
    @Override
    public int select(@NotNull final List<Double> fitnessValues) {
        double r = Math.random();
        for (int i = 0; i < fitnessValues.size(); i++) {
            final double currentFitness = fitnessValues.get(i);
            if (r < currentFitness)
                return i;
            r -= currentFitness;
        }
        return fitnessValues.size()-1;
    }
}
