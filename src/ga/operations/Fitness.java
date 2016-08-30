package ga.operations;

import ga.components.GeneticMaterial;

/**
 * Created by david on 27/08/16.
 */
public interface Fitness {
    double evaluate(GeneticMaterial phenotype);
    // void signalChange();
}
