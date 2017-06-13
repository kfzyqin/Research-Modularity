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
 * @author Siu Kei Muk (David) and Zhenyue Qin
 * @since 11/09/16.
 */
public class SimpleTournamentScheme implements SelectionScheme {

    private int size;
    private double dominanceProbability;

    /**
     * Constructs a tournament selection scheme.
     *
     * @param size tournament size
     * @param dominanceProbability probability p of individual with highest probability to be selected
     */
    public SimpleTournamentScheme(final int size, final double dominanceProbability) {
        sizeFilter(size);
        probabilityFilter(dominanceProbability);
        this.size = size;
        this.dominanceProbability = dominanceProbability;
    }

    private void sizeFilter(final int size) {
        if (size < 1)
            throw new IllegalArgumentException("Size must be at least 1.");
    }

    private void probabilityFilter(final double dominanceProbability) {
        if (dominanceProbability <= 0 || dominanceProbability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    /**
     * @param candidates candidates selected in the tournament pool
     * @return index of selected individual
     */
    private int getSurvivor(@NotNull final List<Integer> candidates) {
        int currentIndex = 0;
        double r = Math.random();
        for (int i = 0; i < size; i++) {
            currentIndex = candidates.get(i);
            if (r < dominanceProbability)
                break;
            r = (r - dominanceProbability)/(1-dominanceProbability);
        }
        return currentIndex;
    }

    /**
     *
     * @param fitnessValues descending sorted fitness function values of individuals.
     * @return
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
        return getSurvivor(indices);
    }
}
