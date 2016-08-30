package ga.operations;

import com.sun.istack.internal.NotNull;
import ga.components.GeneticMaterial;

/**
 * Created by david on 27/08/16.
 */
public interface Fitness<T extends GeneticMaterial> {
    double evaluate(@NotNull final T phenotype);
    // void signalChange();
}
