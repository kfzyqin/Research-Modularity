package experiment2;

import ga.components.genes.GeneFactory;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 11/09/16.
 */
public class NgWongGeneFactory implements GeneFactory<Character> {

    private static final char[] values = {'0','o','1','i'};

    @Override
    public NgWongSchemeGene generateGene() {
        return new NgWongSchemeGene(values[ThreadLocalRandom.current().nextInt(values.length)]);
    }
}
