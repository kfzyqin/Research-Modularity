package ga.components.genes;

import com.sun.istack.internal.NotNull;
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
 * Gene represents a gene, a container of a single value of generic type V.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/2016
 * @param <V> Class/Type of value.
 */
public abstract class Gene<V> implements Copyable<Gene<V>> {

    protected V value;

    public Gene(@NotNull V value) {
        this.value = value;
    }

    /**
     * @return value of the gene
     */
    public V getValue(){
        return value;
    }

    /**
     * @param value value to be assigned to the gene, may be subject to restrictions according to implementations.
     */
    public abstract void setValue(@NotNull final V value);

    @Override
    public boolean equals(Object aGene) {
        try {
            return this.value.equals(((Gene) aGene).getValue());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
