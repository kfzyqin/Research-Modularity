package ga.operations.selectors;

import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by david on 9/09/16.
 */
public class ProportionateScheme implements SelectionScheme{

    public ProportionateScheme() {
    }

    @Override
    public int select(@NotNull final List<Double> fitnessValues) {
        double r = Math.random();
        for (int i = 0; i < fitnessValues.size(); i++) {
            final double currentFitness = fitnessValues.get(i);
            if (r < currentFitness)
                return i;
            r -= currentFitness;
        }
        return fitnessValues.size()-1;
    }
}
