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
 * @author Siu Kei Muk (David)
 * @since 2/09/16.
 */
public class NgWongSchemeGene extends Gene<Character>{

    // private static final char[] values = {'0','o','1','i'};
    // private char value;

    public NgWongSchemeGene(char value) {
        super(value);
        validityFilter(value);
        // this.value = value;
    }

    private void validityFilter(char value) {
        switch (value) {
            case 'i':
            case '1':
            case '0':
            case 'o':
                return;
            default:
                throw new IllegalArgumentException("Invalid value given. The value must be one of 'i', '1', '0' or 'o'.");
        }

    }

    @Override
    public Gene<Character> copy() {
        return new NgWongSchemeGene(value);
    }

    @Override
    public void setValue(@NotNull Character value) {
        validityFilter(value);
        this.value = value;
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }
}
