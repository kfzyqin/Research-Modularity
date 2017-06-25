package ga.operations.fitnessFunctions;

import com.sun.istack.internal.NotNull;
import ga.components.materials.Material;

/**
 * Created by zhenyueqin on 19/6/17.
 */
public interface FitnessFunctionMultipleTargets<P extends Material> extends FitnessFunction<P> {
    /**
     *
     * @param phenotype phenotype of an individual
     * @param generation current population generation
     * @return fitness function value
     */
    double evaluate(@NotNull final P phenotype, int generation);
}
