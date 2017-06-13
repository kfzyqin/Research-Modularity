package ga.components.materials;

import ga.components.genes.Gene;

import java.util.ArrayList;
import java.util.Arrays;
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
 * SimpleMaterial is an implementation where the genes are stored in a List.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 */
public class SimpleMaterial implements Material {

    protected final Gene[] strand;
    protected final int size;

    /**
     * Constructs a SimpleMaterial by a list of genes.
     * @param strand a list of genes
     */
    public SimpleMaterial(List<? extends Gene> strand) {
        size = strand.size();
        this.strand = new Gene[size];
        for (int i = 0; i < size; i++) this.strand[i] = strand.get(i);
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public Gene getGene(final int index){
        return strand[index];
    }

    /**
     * Constructs a deep copy of SimpleMaterial
     * @return a deep copy of the current SimpleMaterial
     */
    @Override
    public SimpleMaterial copy() {
        List<Gene> strand = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            strand.add((Gene)this.strand[i].copy());
        return new SimpleMaterial(strand);
    }

    /**
     * @return String representation of the list of genes.
     */
    @Override
    public String toString() {
        return Arrays.toString(strand);
    }
}
