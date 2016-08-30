package experiment1;

import ga.collections.Population;
import ga.components.*;
import ga.operations.Initializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 31/08/16.
 */
public class Exp1Initializer implements Initializer<SequentialHaploid> {

    private GeneConfig<Integer> config = new GeneConfig<>(0, 1);
    private int size;

    public Exp1Initializer(final int size) {
        this.size = size;
    }

    @Override
    public Population<SequentialHaploid> initialize() {
        Population<SequentialHaploid> population = new Exp1Population(size);
        while (!population.isReady()) {
            final int num = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
            population.addChild(new SequentialHaploid(makeStrand(num)));
        }
        population.nextGeneration();
        return population;
    }

    private DNAStrand makeStrand(final int num) {
        Gene[] genes = new Gene[32];
        for (int i = 0; i < 32; i++) {
            genes[i] = new Gene<Integer>(Integer.class, config, (num >> 31-i) & 1);
        }
        return new DNAStrand(genes);
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }
}
