package ga.components.genes;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 1/09/16.
 */
public class BinaryGene implements Gene<Integer> {

    private int value;

    public BinaryGene(final int value) {
        if (value != 0 && value != 1)
            throw new IllegalArgumentException("Only binary values are allowed.");
        this.value = value;
    }

    public BinaryGene() {
        value = ThreadLocalRandom.current().nextInt(2);
    }

    @Override
    public Gene<Integer> copy() {
        return new BinaryGene(value);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(@NotNull Integer value) {
        if (value != 0 && value != 1)
            throw new IllegalArgumentException("Only binary values are allowed.");
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
