package ga.operations.selectors;

import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by david on 3/09/16.
 */
public class SimpleSelector implements Selector {
    @Override
    public int select(@NotNull final List<Double> fitnessValues) {
        double r = Math.random();
        for (int i = 0; i < fitnessValues.size(); i++) {
            final double value = fitnessValues.get(i);
            if (r < value) return i;
            r -= value;
        }
        return fitnessValues.size()-1;
    }
}
