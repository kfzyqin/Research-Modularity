package ga.operations;

import com.sun.istack.internal.NotNull;
import ga.components.materials.GeneticMaterial;
import ga.others.Copyable;

import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public interface DominanceMapping<M extends GeneticMaterial, G extends GeneticMaterial> extends Copyable<DominanceMapping<M,G>> {
    G map(@NotNull final List<M> materials);
}
