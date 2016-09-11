package ga.components.genes;

import java.util.concurrent.ThreadLocalRandom;

/**
 * BinaryGeneFactory generates random BinaryGene.
 *
 * @author Siu Kei Muk (David)
 * @since 11/09/2016.
 */
public class BinaryGeneFactory implements GeneFactory<Integer> {

    @Override
    public BinaryGene generateGene() {
        return new BinaryGene(ThreadLocalRandom.current().nextInt(2));
    }
}
