package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.Chromosome;
import ga.components.Individual;

import java.util.List;

/**
 * Created by david on 31/08/16.
 */
public interface Statistics<T extends Chromosome> {
    void record(@NotNull final List<Individual<T>> data);
    void print(final int generation);
    void save(@NotNull final String filename);
    void nextGeneration();
}
