package ga.operations.fitness;

import com.sun.istack.internal.NotNull;
import ga.components.materials.GeneticMaterial;

/**
 * This interface provides an abstraction for the fitness function (objective function) for genetic algorithm.
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public interface Fitness<G extends GeneticMaterial> {
    /**
     * Evaluates the fitness function value of the given phenotype.
     *
     * @param phenotype phenotype of an individual
     * @return fitness function value
     */
    double evaluate(@NotNull final G phenotype);
    void update();
}
