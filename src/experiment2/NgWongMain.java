package experiment2;

import ga.components.chromosome.SequentialDiploid;
import ga.components.materials.DNAStrand;
import ga.operations.fitness.Fitness;
import ga.operations.initializers.Initializer;
import ga.operations.mutators.Mutator;

/**
 * Created by david on 11/09/16.
 */
public class NgWongMain {

    private static final int size = 100;
    private static final int maxGen = 2000;
    private static final int numElites = 20;
    private static final int length = 100;
    private static final double mutationRate = 0.05;
    // private static final double epsilon = .5;
    private static final double rho = 0.1;
    // private static final double maxFit = 32;
    private static final String outfile = "NgWong.out";

    public static void main(String[] args) {
        Fitness<DNAStrand> fitness = new DynamicOneMax(length, rho);
        Initializer<SequentialDiploid> initializer = new NgWongInitializer(length, size);

    }
}
