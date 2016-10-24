package experiment3;

import com.sun.istack.internal.NotNull;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;

/**
 * Created by david on 30/09/16.
 */
public class Exp3Fitness implements FitnessFunction<SimpleMaterial> {
    @Override
    public double evaluate(@NotNull final SimpleMaterial phenotype) {
        int sum = 0;
        for (int i = 0; i < phenotype.getSize(); i++) {
            sum += (Integer) phenotype.getGene(i).getValue();
        }
        return sum;
    }

    @Override
    public void update() {

    }
}
