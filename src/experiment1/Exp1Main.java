package experiment1;

import ga.collections.Statistics;
import ga.components.chromosome.SequentialHaploid;
import ga.frame.GAFrame;
import ga.frame.SimpleGAFrame;
import ga.operations.*;
import ga.operations.fitness.Fitness;
import ga.operations.initializers.Initializer;
import ga.operations.mutators.Mutator;
import ga.operations.recombiners.Recombiner;
import ga.operations.selectors.Selector;

/**
 * Created by david on 31/08/16.
 */
public class Exp1Main {

    private static final int target = 0xf71b72e5;
    private static final int size = 200;
    private static final int maxGen = 2000;
    private static final int numElites = 20;
    private static final double mutationRate = 0.05;
    private static final double epsilon = .5;
    private static final double maxFit = 32;
    private static final String outfile = "Exp1.out";

    public static void main(String[] args) {
        Fitness fitness = new Exp1Fitness(target);
        Initializer<SequentialHaploid> initializer = new Exp1Initializer(size);
        Mutator mutator = new Exp1Mutator(mutationRate);
        Selector selector = new Exp1Selector();
        PriorOperator<SequentialHaploid> priorOperator = new Exp1PriorOperator(numElites, selector);
        Statistics<SequentialHaploid> statistics = new Exp1Statistics();
        Recombiner<SequentialHaploid> recombiner = new Exp1Recombiner();

        GAFrame<SequentialHaploid> frame = new SimpleGAFrame<>(fitness,initializer,recombiner,mutator,selector,statistics);
        frame.setPriorOperator(priorOperator);
        statistics.print(0);
        for (int i = 1; i <= maxGen; i++) {
            frame.evolve();
            statistics.print(i);
            if (statistics.getBest(i) > maxFit - epsilon)
                break;
        }
        statistics.save(outfile);
        System.out.println(Integer.toBinaryString(target));
    }
}
