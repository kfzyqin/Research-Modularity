package ga.operations.fitness;

import com.sun.istack.internal.NotNull;
import ga.components.materials.GeneticMaterial;

/**
 * Created by david on 27/08/16.
 */
public interface Fitness<G extends GeneticMaterial> {
    double evaluate(@NotNull final G phenotype);
}
