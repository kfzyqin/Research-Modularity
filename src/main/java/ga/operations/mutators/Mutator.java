package ga.operations.mutators;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;

import java.util.List;

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
 * This interface provides an abstraction for mutators operation.
 * An implementation strategy is to have a 3-layer nested loop (individual-layer, chromosomes-layer, gene-layer) to loop
 * through every gene, and use a gene-value generator to do the random draw.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public interface Mutator<C extends Chromosome> {
    /**
     * Mutates the given list of individuals.
     * @param individuals individuals to be mutated
     */
    void mutate(@NotNull final List<Individual<C>> individuals);
}
