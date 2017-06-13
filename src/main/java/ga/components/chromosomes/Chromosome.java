package ga.components.chromosomes;

import com.sun.istack.internal.NotNull;
import ga.components.materials.Material;
import ga.operations.expressionMaps.ExpressionMap;
import ga.others.Copyable;

import java.util.Collections;
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
 * This class represents a general chromosomes that encodes a candidate solution of an optimization problem.
 * A chromosomes comprises a genotype, which consists of a number of genetic genotype.
 *
 * @author Siu Kei Muk (David)
 * @since 26/08/16.
 * @param <M> class/type of genetic material as the genotype
 * @param <P> class/type of genetic material as the phenotype
 */
public abstract class Chromosome<M extends Material, P extends Material> implements Copyable<Chromosome> {

    protected final int strands;
    protected final int length;
    protected List<M> genotype;
    protected ExpressionMap<M, P> mapping;
    protected P phenotype = null;

    /**
     * Constructs a chromosomes. Note that the 'genotype' field is not initialized.
     * It is recommended to use a FixedSizedList from Apache Commons Collections Library.
     *
     * @param strands ploidy of the chromosomes
     * @param length number of genes of each haploid in the genotype
     */
    public Chromosome(final int strands, final int length,
                      @NotNull ExpressionMap<M, P> mapping) {
        this.strands = strands;
        this.length = length;
        this.mapping = mapping;
    }

    /**
     *
     * @param recompute determines whether to force remapping of phenotype from genotype
     * @return phenotype of the chromosomes
     */
    public P getPhenotype(final boolean recompute) {
        if (phenotype == null || recompute)
            phenotype = mapping.map(genotype);
        return phenotype;
    }

    /**
     * @return ploidy of chromosomes
     */
    public int getStrands() {
        return strands;
    }

    /**
     * @return number of genes of each haploid in the genotype
     */
    public int getLength() {
        return length;
    }

    /**
     * @return an unmodifiable list containing the genotypes
     */
    public List<M> getMaterialsView() {
        return Collections.unmodifiableList(genotype);
    }

    /**
     * @return current genotype-to-phenotype mapping of the chromosomes
     */
    public ExpressionMap<M, P> getMapping() {
        return mapping;
    }

    /**
     * @param mapping genotype-to-phenotype mapping to be set
     */
    public void setMapping(@NotNull final ExpressionMap<M, P> mapping) {
        this.mapping = mapping;
    }
}
