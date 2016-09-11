package ga.operations.selectors;

import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 11/09/16.
 */
public class SimpleTournamentScheme implements SelectionScheme {

    private int size;
    private double dominanceProbability;

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
