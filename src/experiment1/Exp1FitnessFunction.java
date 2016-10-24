package experiment1;

import com.sun.istack.internal.NotNull;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 30/08/16.
 */
public class Exp1FitnessFunction implements FitnessFunction<SimpleMaterial> {

    private final int target;
    private final String targetBitString;

    public Exp1FitnessFunction() {
        this.target = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        targetBitString = Integer.toBinaryString(target);
    }

    public Exp1FitnessFunction(final int target) {
        this.target = target;
        targetBitString = Integer.toBinaryString(target);
    }

    public int getTarget() {
        return target;
    }

    public String getTargetBitString() {
        return targetBitString;
    }

    @Override
    public double evaluate(@NotNull final SimpleMaterial phenotype) {
        int num = 0;
        for (int i = 0; i < phenotype.getSize(); i++) {
            num = (num << 1) | (Integer) phenotype.getGene(i).getValue();
        }
        final int comparison = ~(num ^ target);
        return Integer.bitCount(comparison);
    }

    @Override
    public void update() {

    }
}
