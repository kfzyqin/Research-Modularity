package ga.operations;

import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public interface Selector {
    int select(@NotNull final List<Double> fitnessValues);
}
