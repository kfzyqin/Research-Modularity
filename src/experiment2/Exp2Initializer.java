package experiment2;

import ga.collections.Population;
import ga.components.chromosome.SequentialDiploid;
import ga.operations.initializers.Initializer;

/**
 * Created by david on 8/09/16.
 */
public class Exp2Initializer implements Initializer<SequentialDiploid> {

    private int size;
    private int length;

    public Exp2Initializer(final int size) {
        setSize(size);
    }

    private void filter(final int size) {
        if (size < 1)
            throw new IllegalArgumentException("Population size must be at least 1.");
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        filter(size);
        this.size = size;
    }

    @Override
    public Population<SequentialDiploid> initialize() {
        return null;
    }
}
