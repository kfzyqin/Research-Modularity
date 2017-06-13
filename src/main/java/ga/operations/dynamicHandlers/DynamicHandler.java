package ga.operations.dynamicHandlers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.frame.State;

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
 * This interface provides an abstraction of handler that handles changes occurred in the fitness function/environment.
 * Usually, it is used to re-evaluate the fitness function values and perform dominance change after a change is occurred.
 *
 * @author Siu Kei Muk (David)
 * @since 3/09/16.
 */
public interface DynamicHandler<C extends Chromosome> {
    /**
     * Handles the change in environment.
     *
     * @param state current state of genetic algorithm
     * @return false: not handled, true: handled
     */
    boolean handle(@NotNull final State<C> state);
}
