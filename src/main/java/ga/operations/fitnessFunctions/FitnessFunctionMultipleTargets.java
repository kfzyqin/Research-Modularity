package ga.operations.fitnessFunctions;

import ga.components.materials.Material;
import org.jetbrains.annotations.NotNull;

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
