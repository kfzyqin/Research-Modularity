package ga.operations.fitnessFunctions;

import com.sun.istack.internal.NotNull;
import ga.components.materials.Material;

/**
 * This interface provides an abstraction for the fitness function (objective function) for genetic algorithm.
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public interface FitnessFunction<P extends Material> {
    /**
     * Evaluates the fitness function value of the given phenotype.
     *
     * @param phenotype phenotype of an individual
     * @return fitness function value
     */
    double evaluate(@NotNull final P phenotype);
    void update();
}
