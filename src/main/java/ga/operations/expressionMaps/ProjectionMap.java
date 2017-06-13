package ga.operations.expressionMaps;

import com.sun.istack.internal.NotNull;
import ga.components.materials.Material;

import java.util.List;

/*
    GASEE is a Java-based genetic algorithm library for scientific exploration and experiment.
    Copyright 2016 Siu-Kei Muk

    This file is part of GASEE.

    GASEE is free library: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 2.1 of the License, or
    (at your option) any later version.

    GASEE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with GASEE.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * A projection mapping object returns one of the given genetic material as the phenotype.
 * It is mainly used for identity mapping for haploids.
 *
 * @author Siu Kei Muk (David)
 * @since 12/09/16.
 */
public class ProjectionMap<M extends Material> implements ExpressionMap<M,M> {

    private int projectionIndex;

    public ProjectionMap(final int projectionIndex) {
        setProjectionIndex(projectionIndex);
    }

    private void filter(final int projectionIndex) {
        if (projectionIndex < 0)
            throw new IllegalArgumentException("Projection index must be non-negative.");
    }

    @Override
    public ExpressionMap<M, M> copy() {
        return new ProjectionMap<>(projectionIndex);
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
