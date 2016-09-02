package ga.others;

import experiment1.Exp1Fitness;
import ga.components.genes.BinaryGene;
import ga.components.materials.DNAStrand;
import ga.components.genes.Gene;
import ga.components.GeneConfig;
import ga.operations.fitness.Fitness;

import java.util.Arrays;

/**
 * Created by david on 30/08/16.
 */
public class Test {

    private static GeneConfig<Integer> config = new GeneConfig<>(0,1);

    public static void main(String[] args) {
        final int target = 0xf71b72e5;
        Fitness<DNAStrand> fitness = new Exp1Fitness(target);
        // GeneConfig<Integer> config = new GeneConfig<>(0,1);
        DNAStrand targetStrand = makeStrand(target);
        DNAStrand oneStrand = makeStrand(-1);
        System.out.println(fitness.evaluate(targetStrand));
        System.out.println(fitness.evaluate(oneStrand));
        System.out.println(Integer.bitCount(target));
        System.out.println(((Exp1Fitness) fitness).getTargetBitString());
    }


    private static DNAStrand makeStrand(final int num) {
        Gene[] genes = new Gene[32];
        for (int i = 0; i < 32; i++) {
            genes[i] = new BinaryGene((num >> 31-i) & 1);
        }
        return new DNAStrand(Arrays.asList(genes));
    }

}
