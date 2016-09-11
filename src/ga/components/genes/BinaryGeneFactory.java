package ga.components.genes;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 11/09/16.
 */
public class BinaryGeneFactory implements GeneFactory<Integer> {

    @Override
    public BinaryGene generateGene() {
        return new BinaryGene(ThreadLocalRandom.current().nextInt(2));
    }
}
