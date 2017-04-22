package examples.experiment4;

import com.sun.istack.internal.NotNull;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class GRNFitnessFunction implements FitnessFunction<SimpleMaterial>{

    @Override
    public double evaluate(@NotNull SimpleMaterial phenotype) {
        int ones = 0;
        for (int i=0; i<phenotype.getSize(); i++) {
            int aPosition = (Integer) phenotype.getGene(i).getValue();
            if (aPosition == 1) {
                ones += 1;
            }
        }
        return ones;
    }

    @Override
    public void update() {

    }
}
