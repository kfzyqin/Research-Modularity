package experiment2;

import com.sun.istack.internal.NotNull;
import ga.components.genes.BinaryGene;
import ga.components.genes.Gene;
import ga.components.materials.SimpleDNA;
import ga.operations.fitness.Fitness;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2/09/16.
 */
public class DynamicOneMax implements Fitness<SimpleDNA>{

    private SimpleDNA mask;
    private double flipProb;
    private final int length;

    public DynamicOneMax(final int length, final double flipProb) {
        if (flipProb > 1 || flipProb < 0)
            throw new IllegalArgumentException("Bit-flipping probability must be between 0 and 1.");
        this.flipProb = flipProb;
        this.length = length;
        List<Gene<Integer>> strand = new ArrayList<>(length);
        for (int i = 0; i < length; i++)
            strand.add(new BinaryGene(0));
        mask = new SimpleDNA(strand);
    }

    public SimpleDNA getMask() {
        return mask;
    }

    private void flipGene(BinaryGene gene) {
        gene.setValue(gene.getValue() ^ 1);
    }

    @Override
    public void update() {
        for (int i = 0; i < length; i++)
            if (Math.random() < flipProb)
                flipGene((BinaryGene) mask.getGene(i));
    }

    @Override
    public double evaluate(@NotNull SimpleDNA phenotype) {
        double fitness = 0;
        for (int i = 0; i < length; i++)
            fitness += (int) phenotype.getGene(i).getValue() ^ (int) mask.getGene(i).getValue();
        return fitness;
    }
}
