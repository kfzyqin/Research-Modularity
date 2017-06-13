package ga.operations.expressionMaps;

import com.sun.istack.internal.NotNull;
import ga.components.materials.Material;
import ga.others.Copyable;

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
 * This interface abstracts the genotype-to-phenotype mapping.
 *
 * @author Siu Kei Muk (David)
 * @since 27/08/16.
 */
public interface ExpressionMap<M extends Material, P extends Material> extends Copyable<ExpressionMap<M, P>> {
    /**
     * Performs the genotype-to-phenotype mapping.
     *
     * @param materials genotype of an individual
     * @return phenotype of an individual
     */
    P map(@NotNull final List<M> materials);
}
