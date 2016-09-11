package experiment2;

import ga.components.genes.GeneFactory;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 11/09/16.
 */
public class RyanAdditiveGeneFactory implements GeneFactory<Character> {

    private static final char[] values = {'A','B','C','D'};

    @Override
    public RyanAdditiveSchemeGene generateGene() {
        return new RyanAdditiveSchemeGene(values[ThreadLocalRandom.current().nextInt(values.length)]);
    }
}
