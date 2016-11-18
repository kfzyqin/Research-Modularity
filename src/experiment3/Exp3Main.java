package experiment3;

import genderGAWithHotspots.collections.GenderPopulation;
import genderGAWithHotspots.collections.SimpleGenderElitesStatistics;
import genderGAWithHotspots.components.chromosomes.SimpleGenderDiploid;
import ga.frame.Frame;
import genderGAWithHotspots.frame.GenderState;
import genderGAWithHotspots.frame.SimpleGenderFrame;
import genderGAWithHotspots.frame.SimpleGenderState;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.mutators.BinaryGeneMutator;
import ga.operations.mutators.Mutator;
import genderGAWithHotspots.operations.hotspotMutators.HotspotMutator;
import genderGAWithHotspots.operations.hotspotMutators.SimpleDiscreteExpHotspotMutator;
import ga.operations.postOperators.SimpleFillingOperator;
import genderGAWithHotspots.operations.priorOperators.SimpleGenderElitismOperator;
import genderGAWithHotspots.operations.reproducers.CoupleReproducer;
import genderGAWithHotspots.operations.reproducers.SimpleGenderDiploidReproducer;
import genderGAWithHotspots.operations.selectors.CoupleSelector;
import genderGAWithHotspots.operations.selectors.SimpleTournamentCoupleSelector;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;

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
 * @since 30/09/16.
 */
public class Exp3Main {

    private static final int target = 0xf71b72e5;
    private static final int size = 200;
    private static final int maxGen = 2000;
    private static final int numElites = 10;
    private static final int tournamentSize = 2;
    private static final double selectivePressure = 0.7;
    private static final double mutationRate = 0.05;
    private static final double crossoverRate = .8;
    private static final double epsilon = .5;
    private static final int maxFit = 32;
    private static final String outfile = "Exp3.out";

    public static void main(String[] args) {
        // Fitness Function
        FitnessFunction fitnessFunction = new Exp3Fitness();
        // Initializer
        Exp3Initializer initializer = new Exp3Initializer(size, maxFit);
        // Population
        GenderPopulation<SimpleGenderDiploid<Integer>> population = initializer.initialize();
        // Mutator
        Mutator<SimpleGenderDiploid<Integer>> mutator = new BinaryGeneMutator<>(mutationRate);
        // Selector for reproduction
        CoupleSelector<SimpleGenderDiploid<Integer>> selector = new SimpleTournamentCoupleSelector<>(tournamentSize, selectivePressure);
        // Elitism operator for gender-based diploids
        SimpleGenderElitismOperator<SimpleGenderDiploid<Integer>> priorOperator = new SimpleGenderElitismOperator<>(numElites);
        // Simple filling operator
        SimpleFillingOperator<SimpleGenderDiploid<Integer>> postOperator = new SimpleFillingOperator<>(new SimpleTournamentScheme(tournamentSize, selectivePressure));
        // Statistics
        SimpleGenderElitesStatistics<SimpleGenderDiploid<Integer>> statistics = new SimpleGenderElitesStatistics<>(maxGen);
        // Reproducer
        CoupleReproducer<SimpleGenderDiploid<Integer>> recombinator = new SimpleGenderDiploidReproducer(1);
        // Hotspot mutators
        HotspotMutator<Integer> hotspotMutator = new SimpleDiscreteExpHotspotMutator(1);

        GenderState<SimpleGenderDiploid<Integer>,Integer> state = new SimpleGenderState<>(
                population, fitnessFunction, mutator, recombinator, selector, hotspotMutator, crossoverRate);

        Frame<SimpleGenderDiploid<Integer>> frame = new SimpleGenderFrame<>(state, postOperator, statistics);

        frame.setPriorOperator(priorOperator);

        state.record(statistics);

        statistics.print(0);

        for (int i = 1; i <= maxGen; i++) {
            frame.evolve();
            statistics.print(i);
            if (statistics.getOptimum(i) > maxFit - epsilon) break;
        }

        statistics.save(outfile);
    }
}
