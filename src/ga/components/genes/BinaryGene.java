package ga.components.genes;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * BinaryGene is an implementation of binary genes. It can only store 0 or 1 as its value.
 *
 * @author Siu Kei Muk (David)
 * @since 01/09/2016
 */
public class BinaryGene extends Gene<Integer> {

    public static BinaryGene generateRandomBinaryGene() {
        return new BinaryGene((Math.random() < 0.5) ? 0 : 1);
    }

    // private int value;

    /**
     * Constructs a binary gene with the specified binary value.
     * @param value 0 or 1
     * @throws IllegalArgumentException If the value is not 0 or 1.
     */
    public BinaryGene(final int value) {
        super(value);
        if (value != 0 && value != 1)
            throw new IllegalArgumentException("Only binary values are allowed.");
        // this.value = value;
    }

    /**
     * Constructs a binary gene with random binary value.
     */
    public BinaryGene() {
        super(ThreadLocalRandom.current().nextInt(2));
    }

    /**
     * Constructs a deep copy of the current gene.
     * @return A new binary gene with the same value.
     */
    @Override
    public Gene<Integer> copy() {
        return new BinaryGene(value);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    /**
     *
     * @param value Value (0 or 1) to be assigned to the gene.
     * @throws IllegalArgumentException If value is not 0 or 1.
     */
    @Override
    public void setValue(@NotNull Integer value) {
        if (value != 0 && value != 1)
            throw new IllegalArgumentException("Only binary values are allowed.");
        this.value = value;
    }

    /**
     * @return String representation of the gene value.
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
