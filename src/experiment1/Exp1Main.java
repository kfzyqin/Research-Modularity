package experiment1;

import ga.collections.Population;
import ga.collections.Statistics;
import ga.components.chromosomes.SimpleHaploid;
import ga.frame.Frame;
import ga.frame.State;
import ga.frame.SimpleFrame;
import ga.frame.SimpleState;
import ga.operations.fitnessfunction.FitnessFunction;
import ga.operations.initializers.BinarySimpleHaploidInitializer;
import ga.operations.initializers.Initializer;
import ga.operations.mutators.Mutator;
import ga.operations.postOperators.PostOperator;
import ga.operations.postOperators.SimpleFillingOperatorForNormalizable;
import ga.operations.priorOperators.PriorOperator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectionSchemes.ProportionateScheme;
import ga.operations.selectionOperators.selectors.Selector;
import ga.operations.selectionOperators.selectors.SimpleProportionateSelector;

/**
 * Created by david on 31/08/16.
 */
public class Exp1Main {

    private static final int target = 0xf71b72e5;
    private static final int size = 200;
    private static final int maxGen = 2000;
    private static final int numElites = 20;
    private static final double mutationRate = 0.05;
    private static final double crossoverRate = .8;
    private static final double epsilon = .5;
    private static final double maxFit = 32;
    private static final String outfile = "Exp1.out";

    public static void main(String[] args) {
        // Fitness Function
        FitnessFunction fitnessFunction = new Exp1FitnessFunction(target);
        // It is not necessary to write an initializer, but doing so is convenient to repeat the experiment
        // using different parameter.
        Initializer<SimpleHaploid> initializer = new BinarySimpleHaploidInitializer(size, 32);
        // Population
        Population<SimpleHaploid> population = initializer.initialize();
        // Mutator for chromosomes
        Mutator mutator = new Exp1Mutator(mutationRate);
        // Selector for reproduction
        Selector<SimpleHaploid> selector = new SimpleProportionateSelector<>();
        // PriorOperator is optional.
        PriorOperator<SimpleHaploid> priorOperator = new Exp1PriorOperator(numElites, selector);
        // PostOperator is required to fill up the vacancy.
        PostOperator<SimpleHaploid> postOperator = new SimpleFillingOperatorForNormalizable<>(new ProportionateScheme());
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
        System.out.println(Integer.toBinaryString(target));
    }
}
