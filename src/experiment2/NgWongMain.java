package experiment2;

import ga.collections.Population;
import ga.collections.SimpleElitesStatistics;
import ga.collections.Statistics;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.materials.SimpleMaterial;
import ga.frame.Frame;
import ga.frame.State;
import ga.frame.SimpleFrame;
import ga.frame.SimpleState;
import ga.operations.dynamicHandlers.DynamicHandler;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.initializers.Initializer;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.priorOperators.SimpleElitismOperator;
import ga.operations.reproducers.Reproducer;
import ga.operations.reproducers.SimpleDiploidReproducer;
import ga.operations.selectionOperators.selectors.Selector;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import ga.operations.selectionOperators.selectors.SimpleTournamentSelector;

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
 * @since 11/09/16.
 */
public class NgWongMain {

    private static final int size = 100;
    private static final int maxGen = 2000;
    private static final int numElites = 1;
    private static final int length = 100;
    private static final double mutationRate = 0.05;
    private static final double recombinationRate = 0.6;
    private static final int cycleLength = 50;
    private static final double dominanceChangeThreshold = 0.2;
    private static final int tournamentSize = 2;
    private static final double selectivePressure = 0.7;
    // private static final double epsilon = .5;
    private static final double rho = 0.1;
    // private static final double maxFit = 32;
    private static final String outfile = "NgWong.out";

    public static void main(String[] args) {
        FitnessFunction<SimpleMaterial> fitnessFunction = new DynamicOneMax(length, rho);
        Initializer<SimpleDiploid> initializer = new NgWongInitializer(length, size);
        Population<SimpleDiploid> population = initializer.initialize();
        Reproducer<SimpleDiploid> reproducer = new SimpleDiploidReproducer();
        Mutator<SimpleDiploid> mutator = new NgWongMutator(mutationRate);
        Selector<SimpleDiploid> selector = new SimpleTournamentSelector<>(tournamentSize, selectivePressure);
        PostOperator<SimpleDiploid> fillingOperator = new SimpleFillingOperatorForNormalizable<>(new SimpleTournamentScheme(tournamentSize, selectivePressure));
        PriorOperator<SimpleDiploid> elitismOperator = new SimpleElitismOperator<>(numElites);
        DynamicHandler<SimpleDiploid> handler = new NgWongDominanceChange(dominanceChangeThreshold, cycleLength);
        Statistics<SimpleDiploid> statistics = new SimpleElitesStatistics<>();

        State<SimpleDiploid> state = new SimpleState<>(population, fitnessFunction, mutator, reproducer, selector, 2, recombinationRate);
        state.record(statistics);
        Frame<SimpleDiploid> frame = new SimpleFrame<>(state, fillingOperator, statistics, handler);
        frame.setPriorOperator(elitismOperator);
        statistics.print(0);

        for (int i = 0; i < maxGen; i++) {
            frame.evolve();
            statistics.print(i);
        }
        statistics.save(outfile);


    }
}
