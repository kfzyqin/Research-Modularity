package ga.operations.dominanceMappings;

import com.sun.istack.internal.NotNull;
import ga.components.materials.GeneticMaterial;

import java.util.List;

/**
 * Created by david on 12/09/16.
 */
public class ProjectionMapping<M extends GeneticMaterial> implements DominanceMapping<M,M> {

    private int projectionIndex;

    public ProjectionMapping(final int projectionIndex) {
        setProjectionIndex(projectionIndex);
    }

    private void filter(final int projectionIndex) {
        if (projectionIndex < 0)
            throw new IllegalArgumentException("Projection index must be non-negative.");
    }

    @Override
    public DominanceMapping<M, M> copy() {
        return new ProjectionMapping<>(projectionIndex);
    }

    @Override
    public M map(@NotNull List<M> materials) {
        return materials.get(projectionIndex);
    }

    public int getProjectionIndex() {
        return projectionIndex;
    }

    public void setProjectionIndex(final int projectionIndex) {
        filter(projectionIndex);
        this.projectionIndex = projectionIndex;
    }
}
