package ga.components.genes;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/*
    GASEE is a Java-based genetic algorithm library for scientific exploration and experiment.
    Copyright 2016 Siu-Kei Muk

    This file is part of GASEE.

    GASEE is free library: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 2.1 of the License, or
    (at your option) any later version.

    GASEE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with GASEE.  If not, see <http://www.gnu.org/licenses/>.
 */

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
