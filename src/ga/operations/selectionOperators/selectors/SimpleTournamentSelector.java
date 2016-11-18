package ga.operations.selectionOperators.selectors;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;

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
 * This class implements a simple selector that uses tournament selection scheme.
 *
 * @author Siu Kei Muk (David)
 * @since 11/09/16.
 */
public class SimpleTournamentSelector<C extends Chromosome> extends BaseSelector<C> {

    public SimpleTournamentSelector(final int size, final double dominanceProbability) {
        super(new SimpleTournamentScheme(size, dominanceProbability));
    }

    @Override
    public void setSelectionData(@NotNull List<Individual<C>> individuals) {
        this.individuals = individuals;
        fitnessValues.clear();
        for (int i = 0; i < individuals.size(); i++) {
            fitnessValues.add(individuals.get(i).getFitness());
        }
    }
}
