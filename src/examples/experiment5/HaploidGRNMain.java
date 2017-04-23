package examples.experiment5;

import examples.experiment1.Exp1PriorOperator;
import examples.experiment1.Exp1Reproducer;
import examples.experiment1.Exp1Statistics;
import examples.experiment4.GRNFitnessFunction;
import ga.collections.Population;
import ga.collections.Statistics;
import ga.components.chromosomes.SimpleHaploid;
import ga.frame.Frame;
import ga.frame.SimpleFrame;
import ga.frame.SimpleState;
import ga.frame.State;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.initializers.HaploidInitializer;
import ga.operations.initializers.Initializer;
import ga.operations.mutators.GRNEdgeMutator;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectionSchemes.ProportionalScheme;
import ga.operations.selectionOperators.selectors.Selector;
import ga.operations.selectionOperators.selectors.SimpleProportionalSelector;

/**
 * Created by Zhenyue Qin on 23/04/2017.
 * The Australian National University.
 */
public class HaploidGRNMain {
    private static final int[] target = {-1, 1, -1, 1, -1, -1, 1};
    private static final int maxCycle = 30;
    private static final int edgeSize = 10;

    private static final int size = 200;
    private static final int maxGen = 2000;
    private static final int numElites = 20;
    private static final double mutationRate = 0.05;
    private static final double crossoverRate = .8;
    private static final double epsilon = .5;
    private static final double maxFit = 49;
    private static final String outfile = "Exp5.out";

    public static void main(String[] args) {
        // Fitness Function
        FitnessFunction fitnessFunction = new GRNFitnessFunction(target, maxCycle);

        // It is not necessary to write an initializer, but doing so is convenient to repeat the experiment
        // using different parameter.
        Initializer<SimpleHaploid> initializer = new HaploidInitializer(size, target, edgeSize);

        // Population
        Population<SimpleHaploid> population = initializer.initialize();

        // Mutator for chromosomes
        Mutator mutator = new GRNEdgeMutator(mutationRate);

        // Selector for reproduction
        Selector<SimpleHaploid> selector = new SimpleProportionalSelector<>();
        // PriorOperator is optional.
        PriorOperator<SimpleHaploid> priorOperator = new Exp1PriorOperator(numElites, selector);
        // PostOperator is required to fill up the vacancy.
        PostOperator<SimpleHaploid> postOperator = new SimpleFillingOperatorForNormalizable<>(new ProportionalScheme());
        // Statistics for keeping track the performance in generations
        Statistics<SimpleHaploid> statistics = new Exp1Statistics();
        // Reproducer for reproduction
        Reproducer<SimpleHaploid> reproducer = new Exp1Reproducer();

        State<SimpleHaploid> state = new SimpleState<>(population, fitnessFunction, mutator, reproducer, selector, 2, crossoverRate);
        state.record(statistics);
        Frame<SimpleHaploid> frame = new SimpleFrame<>(state,postOperator,statistics);
        frame.setPriorOperator(priorOperator);
        statistics.print(0);
        for (int i = 1; i <= maxGen; i++) {
            frame.evolve();
            statistics.print(i);
            if (statistics.getOptimum(i) > maxFit - epsilon)
                break;
        }
        statistics.save(outfile);
    }
}
