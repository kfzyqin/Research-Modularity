package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.Chromosome;

import java.util.List;
import java.util.Map;

/**
 * Created by david on 29/08/16.
 */
public interface Statistics<T extends Chromosome> {
    void record(@NotNull final Map<String, Object> data);
    void record(@NotNull final String key, Object object);
    void request(final int generation, @NotNull final List<String> keys, @NotNull final Map<String, Object> data);
    Object request(final int generation, @NotNull final String key);
    void nextGeneration();
    void save(@NotNull final String filename);
    // void load(@NotNull final String filename);
}
