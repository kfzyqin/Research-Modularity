package experiment1;

import ga.collections.Population;
import ga.collections.Statistics;
import ga.components.SequentialHaploid;
import ga.frame.GAFrame;
import ga.frame.GAState;
import ga.operations.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by david on 31/08/16.
 */
public class Exp1Main {

    private static final int target = 0xf71b72e5;
    private static final int size = 200;
    private static final int maxGen = 4000;
    private static final double mutationRate = 0.05;
    private static final double epsilon = 1.5;
    private static final String outfile = "Exp1.out";

    public static void main(String[] args) {
        Fitness fitness = new Exp1Fitness(target);
        Initializer<SequentialHaploid> initializer = new Exp1Initializer(size);
        Mutator mutator = new Exp1Mutator(mutationRate);
        Selector selector = new Exp1Selector();
        Statistics<SequentialHaploid> statistics = new Exp1Statistics();
        Recombiner<SequentialHaploid> recombiner = new Exp1Recombiner();
        Map<String, Object> message = new HashMap<>();

        GAFrame<SequentialHaploid> frame = new GAFrame<>(fitness,initializer,recombiner,mutator,selector,statistics);
        for (int i = 0; i < maxGen; i++) {
            frame.evolve();
            //frame.getStatistics().request(i+1, Exp1MessageKeys.DELTA.key,message);
            //double delta =
        }
        statistics.save(outfile);
    }
}
