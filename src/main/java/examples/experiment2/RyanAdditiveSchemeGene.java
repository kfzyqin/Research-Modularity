package examples.experiment2;

import com.sun.istack.internal.NotNull;
import ga.components.genes.Gene;

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
 *
 * @author Siu Kei Muk (David)
 * @since 3/09/16.
 */
public class RyanAdditiveSchemeGene extends Gene<Character> {

    private static final char[] values = {'A','B','C','D'};
    private char value;

    public RyanAdditiveSchemeGene(final char value) {
        super(value);
        setValue(value);
        // this.value = value;
    }

    private void filter(final char value) {
        if (value < 'A' || value > 'D')
            throw new IllegalArgumentException("Invalid value. The permitted values are 'A', 'B', 'C' or 'D'.");
    }

    @Override
    public Gene<Character> copy() {
        return new RyanAdditiveSchemeGene(value);
    }

    @Override
    public void setValue(@NotNull Character value) {
        filter(value);
        this.value = value;
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }
}
