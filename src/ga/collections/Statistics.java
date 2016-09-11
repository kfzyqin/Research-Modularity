package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;
import ga.others.Copyable;

import java.util.List;

/**
 * Created by david on 31/08/16.
 */
public interface Statistics<T extends Chromosome> extends Copyable<Statistics<T>>{

    default void print(final int generation) {
        System.out.println(getSummary(generation));
        System.out.println();
    }
    void record(@NotNull final List<Individual<T>> data);
    void save(@NotNull final String filename);
    void nextGeneration();
    // double getDelta(final int generation);
    double getOptimum(final int generation);
    String getSummary(final int generation);
}
