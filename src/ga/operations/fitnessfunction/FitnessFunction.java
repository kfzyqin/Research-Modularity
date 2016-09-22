package ga.operations.fitnessfunction;

import com.sun.istack.internal.NotNull;
import ga.components.materials.GeneticMaterial;

/**
 * This interface provides an abstraction for the fitnessfunction function (objective function) for genetic algorithm.
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public interface FitnessFunction<G extends GeneticMaterial> {
    /**
     * Evaluates the fitnessfunction function value of the given phenotype.
     *
     * @param phenotype phenotype of an individual
     * @return fitnessfunction function value
     */
    double evaluate(@NotNull final G phenotype);
    void update();
}
