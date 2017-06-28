package ga.operations.reproducers;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.genes.Gene;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zhenyueqin on 21/6/17.
 */
public abstract class DiploidReproducer<C extends Chromosome> implements Reproducer<C> {

    protected double matchProbability = 0.5;
    protected boolean isToDoCrossover = true;

    public DiploidReproducer() {

    }

    public DiploidReproducer(final double matchProbability) {
        setMatchProbability(matchProbability);
    }

    public DiploidReproducer(final double matchProbability, final boolean toDoCrossover) {
        setMatchProbability(matchProbability);
        setIsToDoCrossOver(toDoCrossover);
    }

    private void filter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    public void setMatchProbability(final double matchProbability) {
        filter(matchProbability);
        this.matchProbability = matchProbability;
    }

    public void setIsToDoCrossOver(boolean doCrossover) {
        this.isToDoCrossover = doCrossover;
    }

    @Override
    public List<C> reproduce(@NotNull final List<C> mates) {
        List<C> children = new ArrayList<>();
        children.addAll(recombine(mates));
        return children;
    }

    protected List<SimpleMaterial> crossover(@NotNull final C parent) {
        List<SimpleMaterial> newDNAs = new ArrayList<>(2);
        List<SimpleMaterial> materialView = parent.getMaterialsView();
        SimpleMaterial dna1Copy = materialView.get(0).copy();
        SimpleMaterial dna2Copy = materialView.get(1).copy();

        final int crossIndex = ThreadLocalRandom.current().nextInt(1, dna1Copy.getSize());
        if (isToDoCrossover) {
            crossoverTwoDNAsAt(dna1Copy, dna2Copy, crossIndex);
        }

        newDNAs.add(dna1Copy);
        newDNAs.add(dna2Copy);
        return newDNAs;
    }

    protected abstract List<C> recombine(@NotNull final List<C> mates);

    protected void crossoverTwoDNAsAt(Material dna1, Material dna2, int crossIndex) {
        for (int i = crossIndex; i < dna1.getSize(); i++) {
            final int i1 = ((Gene<Integer>) dna1.getGene(i)).getValue();
            final int i2 = ((Gene<Integer>) dna2.getGene(i)).getValue();
            dna1.getGene(i).setValue(i2);
            dna2.getGene(i).setValue(i1);
        }
    }

    public double getMatchProbability() {
        return matchProbability;
    }

    public boolean isToDoCrossover() {
        return isToDoCrossover;
    }
}
