package ga.collections;

import ga.components.chromosomes.Chromosome;
import ga.others.Copyable;
import org.jetbrains.annotations.NotNull;

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
 * This interface provides an abstraction for statistics recording of the genetic algorithm.
 *
 * @author Siu Kei Muk (David)
 * @since 31/08/16.
 */
public interface Statistics<C extends Chromosome> extends Copyable<Statistics<C>>{

    /**
     * Prints the information of the given generation to STDOUT.
     * @param generation
     */
    default void print(final int generation) {
        System.out.println(getSummary(generation));
    }

    /**
     * Records information from the current generation.
     * @param data list of individuals of the current generation
     */
    void record(@NotNull final List<Individual<C>> data);

    /**
     * Saves the statistics to a file with given filename
     * @param filename
     */
    void save(@NotNull final String filename);

    /**
     * Notifies the statistics bookkeeper that the following record should be in a new generation.
     */
    void nextGeneration();
    // double getDelta(final int generation);

    /**
     * Returns the optimum fitnessFunctions value reached of a given generation.
     * @param generation
     * @return optimum fitnessFunctions value of the given generation
     */
    double getOptimum(final int generation);

    /**
     * Returns a summary of a given generation as string
     * @param generation
     * @return a summary of the given generation
     */
    String getSummary(final int generation);

    int getGeneration();
}
