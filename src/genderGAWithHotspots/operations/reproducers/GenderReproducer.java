package genderGAWithHotspots.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.components.materials.Material;
import genderGAWithHotspots.components.chromosomes.Coupleable;
import ga.components.genes.Gene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 28/09/16.
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
