package ga.operations.selectionOperators.selectionSchemes;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collections;
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
 * This class implements the tournament selection scheme.
 * A number, K, of the individuals are first randomly added in a pool.
 * From the pool, the individual with highest fitness function value will be selected with given probability p.
 * The one with second highest fitness function value will be selection with p(1-p), and the third one with p(1-p)^2, and so on.
 * If none of the first K-1 individual is selected, the last one is selected.
 *
 * @author Siu Kei Muk (David)
 * @since 11/09/16.
 */
public class SimpleTournamentScheme implements SelectionScheme {

    private int size;

    /**
     * Constructs a tournament selection scheme.
     *
     * @param size tournament size
     */
    public SimpleTournamentScheme(final int size) {
        sizeFilter(size);
        this.size = size;
    }

    private void sizeFilter(final int size) {
        if (size < 1)
            throw new IllegalArgumentException("Size must be at least 1.");
    }

    /**
     *
     * @param fitnessValues descending sorted fitness function values of individuals.
     * @return the index of the best individual in the tournament pool.
     */
    @Override
    public int select(@NotNull List<Double> fitnessValues) {
        List<Integer> indices = new ArrayList<>(size);
        final int populationSize = fitnessValues.size();
        while (indices.size() < size) {
            final int index = ThreadLocalRandom.current().nextInt(populationSize);
            if (!indices.contains(index))
                indices.add(index);
        }
        Collections.sort(indices);
        return indices.get(0);
    }
}
