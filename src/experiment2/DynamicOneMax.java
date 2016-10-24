package experiment2;

import com.sun.istack.internal.NotNull;
import ga.components.genes.BinaryGene;
import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2/09/16.
 */
public class DynamicOneMax implements FitnessFunction<SimpleMaterial> {

    private SimpleMaterial mask;
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
        mask = new SimpleMaterial(strand);
    }

    public SimpleMaterial getMask() {
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
    public double evaluate(@NotNull SimpleMaterial phenotype) {
        double fitness = 0;
        for (int i = 0; i < length; i++)
            fitness += (int) phenotype.getGene(i).getValue() ^ (int) mask.getGene(i).getValue();
        return fitness;
    }
}
