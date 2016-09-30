package ga.operations.selectors;

import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * This interface abstracts the selection scheme of genetic algorithm.
 * The list of individuals and the list of given fitness function values must correspond in order.
 *
 * @author Siu Kei Muk (David)
 * @since 9/09/16.
 */
public interface SelectionScheme {

    /**
     * This method assumes the individuals in the population are sorted in descending order for the
     * returned index to locate the selected individual.
     *
     * @param fitnessValues descending sorted fitness function values of individuals.
     * @return index of the selected individual's index/position.
     */
    int select(@NotNull final List<Double> fitnessValues);
}
