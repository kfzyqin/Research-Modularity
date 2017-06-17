package ga.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.Coupleable;
import ga.components.chromosomes.CoupleableWithHotspot;
import ga.components.genes.Gene;
import ga.components.materials.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public abstract class GenderReproducer <G extends Chromosome & Coupleable> implements CoupleReproducer<G> {
    protected int numOfChildren;

    public GenderReproducer(final int numOfChildren) {
        setNumOfChildren(numOfChildren);
    }

    public void setNumOfChildren(final int numOfChildren) {
        filter(numOfChildren);
        this.numOfChildren = numOfChildren;
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

        // We assume that crossover is bound to happen, so the range starts from 1 and end with the second last.
        final int crossIndex = ThreadLocalRandom.current().nextInt(1,dna1.getSize()-1);

        this.crossoverTwoDNAsAt(dna1, dna2, crossIndex);

        gametes.add(dna1);
        gametes.add(dna2);
        return gametes;
    }

    protected void crossoverTwoDNAsAt(Material dna1, Material dna2, int crossIndex) {
        for (int i = crossIndex; i < dna1.getSize(); i++) {
            final int i1 = ((Gene<Integer>) dna1.getGene(i)).getValue();
            final int i2 = ((Gene<Integer>) dna2.getGene(i)).getValue();
            dna1.getGene(i).setValue(i2);
            dna2.getGene(i).setValue(i1);
        }
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }
}
