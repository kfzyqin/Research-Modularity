package ga.operations.selectionOperators.selectionSchemes;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements the tournament selection scheme.
 * A number, K, of the individuals are first randomly added in a pool.
 * From the pool, the individual with highest fitness function value will be selected.
 *
 * @author Siu Kei Muk (David) and Zhenyue Qin
 * @since 11/09/16.
 */
public class SimpleTournamentScheme implements SelectionScheme {

    private int size;
    private Random randomno = new Random();

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
            final int index = randomno.nextInt(populationSize);
            if (!indices.contains(index))
                indices.add(index);
        }
        Collections.sort(indices);
        return indices.get(0);
    }
}
