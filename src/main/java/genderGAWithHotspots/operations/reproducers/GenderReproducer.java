package genderGAWithHotspots.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.components.genes.Gene;
import ga.components.materials.Material;
import genderGAWithHotspots.components.chromosomes.Coupleable;

import java.util.ArrayList;
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
 * This class provides an initial implementation for GenderReproducers for convenience, as the
 * potential implementations of GenderReproducer share a similar structure. Each subclass will
 * only need to provide the body for "recombine" method.
 *
 * @author Siu Kei Muk (David)
 * @since 28/09/16.
 */
public abstract class GenderReproducer<G extends Chromosome & Coupleable> implements CoupleReproducer<G> {

    private int numOfChildren;

    public GenderReproducer(final int numOfChildren) {
        setNumOfChildren(numOfChildren);
    }

    private void filter(final int numOfChildren) {
        if (numOfChildren < 1) throw new IllegalArgumentException("Number of children must be at least 1");
    }

    @Override
    public List<G> reproduce(@NotNull final List<G> mates) {
        List<G> children = new ArrayList<>(numOfChildren);
        for (int i = 0; i < numOfChildren; i++) children.add(recombine(mates));
        return children;
    }

    protected abstract G recombine(@NotNull final List<G> mates);

    protected List<Material> crossover(@NotNull final G parent) {
        List<Material> gametes = new ArrayList<>(2);
        List<Material> materialView = parent.getMaterialsView();
        Material dna1 = materialView.get(0).copy();
        Material dna2 = materialView.get(1).copy();
        List<Double> recombinationRate = parent.getHotspot().getRecombinationRate();
        for (int i = 0; i < recombinationRate.size(); i++) {
            if (Math.random() < recombinationRate.get(i)) {
                Gene gene1 = dna1.getGene(i);
                Gene gene2 = dna2.getGene(i);
                Object value = gene1.getValue();
                gene1.setValue(gene2.getValue());
                gene2.setValue(value);
            }
        }
        gametes.add(dna1);
        gametes.add(dna2);
        return gametes;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(final int numOfChildren) {
        filter(numOfChildren);
        this.numOfChildren = numOfChildren;
    }
}

