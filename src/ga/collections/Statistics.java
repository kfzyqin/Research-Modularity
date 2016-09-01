package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;

import java.util.List;

/**
 * Created by david on 31/08/16.
 */
public interface Statistics<T extends Chromosome> {
    void record(@NotNull final List<Individual<T>> data);
    void print(final int generation);
    void save(@NotNull final String filename);
    void nextGeneration();
    double getDelta(final int generation);
    double getBest(final int generation);
}
