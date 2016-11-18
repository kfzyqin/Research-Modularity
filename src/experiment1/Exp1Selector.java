package experiment1;

import com.sun.istack.internal.NotNull;
import ga.operations.selectionOperators.selectionSchemes.SelectionScheme;

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
 *
 * @author Siu Kei Muk (David)
 * @since 31/08/16.
 */
public class Exp1Selector implements SelectionScheme{

    public Exp1Selector() {
    }

    @Override
    public int select(@NotNull List<Double> fitnessValues) {
        double r = Math.random();
        double currentValue = 0;
        for (int i = 0; i < fitnessValues.size(); i++) {
            currentValue = fitnessValues.get(i);
            if (r < currentValue)
                return i;
            r -= currentValue;
        }
        return fitnessValues.size()-1;
    }
}
