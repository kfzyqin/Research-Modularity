package ga.operations.selectors;

import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by david on 9/09/16.
 */
public interface SelectionScheme {
    int select(@NotNull final List<Double> fitnessValues);
}
