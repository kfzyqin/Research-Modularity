package ga.components.materials;

import ga.components.genes.Gene;
import ga.others.Copyable;

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
 * This interface abstracts an indexable genetic material.
 * A genetic material is a container of genes which can be extracted by index from 0 to size-1.
 * This interface extends the Copyable interface.
 *
 * @author Siu Kei Muk (David)
 * @since 30/08/16.
 */
public interface Material extends Copyable<Material> {
    /**
     * @return Number of genes contained in the material.
     */
    int getSize();

    /**
     * @param index location of gene
     * @return gene located at the specified position
     */
    Gene getGene(final int index);
}
