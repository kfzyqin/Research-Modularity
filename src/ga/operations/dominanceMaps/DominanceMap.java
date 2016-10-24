package ga.operations.dominanceMaps;

import com.sun.istack.internal.NotNull;
import ga.components.materials.Material;
import ga.others.Copyable;

import java.util.List;

/**
 * This interface abstracts the genotype-to-phenotype mapping.
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public interface DominanceMap<M extends Material, P extends Material> extends Copyable<DominanceMap<M, P>> {
    /**
     * Performs the genotype-to-phenotype mapping.
     *
     * @param materials genotype of an individual
     * @return phenotype of an individual
     */
    P map(@NotNull final List<M> materials);
}
