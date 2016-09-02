package ga.operations.fitness;

import ga.components.materials.GeneticMaterial;

/**
 * Created by david on 2/09/16.
 */
public interface DynamicFitness<G extends GeneticMaterial> extends Fitness<G> {
    void update();
}
