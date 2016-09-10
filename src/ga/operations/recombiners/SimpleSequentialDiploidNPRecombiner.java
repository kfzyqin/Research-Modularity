package ga.operations.recombiners;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.SequentialDiploid;
import ga.components.genes.Gene;
import ga.components.materials.DNAStrand;
import ga.operations.dominanceMappings.DominanceMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 8/09/16.
 */
public class SimpleSequentialDiploidNPRecombiner implements Recombiner<SequentialDiploid> {

    private double matchProbability = 0.5;
    private int points = 1;

    public SimpleSequentialDiploidNPRecombiner() {
    }

    public SimpleSequentialDiploidNPRecombiner(final double matchProbability, final int points) {
        setMatchProbability(matchProbability);
        setPoints(points);
    }

    private void probabilityFilter(final double probability) {
        if (probability < 0 || probability > 1)
            throw new IllegalArgumentException("Invalid probability value.");
    }

    private void pointsFilter(final int points) {
        if (points < 1)
            throw new IllegalArgumentException("Invalid number of crossover points.");
    }

    @Override
    public List<SequentialDiploid> recombine(@NotNull List<SequentialDiploid> mates) {
        SequentialDiploid parent1 = mates.get(0);
        SequentialDiploid parent2 = mates.get(1);
        DNAStrand dna1_1 = parent1.getMaterialsView().get(0).copy();
        DNAStrand dna1_2 = parent2.getMaterialsView().get(0).copy();
        DNAStrand dna2_1 = parent1.getMaterialsView().get(1).copy();
        DNAStrand dna2_2 = parent2.getMaterialsView().get(1).copy();
        DominanceMapping mapping1 = parent1.getMapping();
        DominanceMapping mapping2 = parent2.getMapping();

        if (Math.random() < matchProbability) {
            DNAStrand tmp = dna1_2;
            dna1_2 = dna2_2;
            dna2_2 = tmp;
        }

        if (Math.random() < matchProbability) {
            DominanceMapping tmp = mapping1;
            mapping1 = mapping2;
            mapping2 = tmp;
        }

        swap(dna1_1, dna1_2);
        swap(dna2_1, dna2_2);

        List<SequentialDiploid> children = new ArrayList<>(2);
        children.add(new SequentialDiploid(dna1_1,dna1_2, mapping1));
        children.add(new SequentialDiploid(dna2_1,dna2_2, mapping2));

        return children;
    }

    private void swap(DNAStrand dna1, DNAStrand dna2) {
        final int length = dna1.getSize();
        List<Integer> crossoverPoints = generateCrossoverPoints(length);
        int index = 0;
        int crossIndex = crossoverPoints.get(0);
        boolean swap = false;
        for (int i = 0; i < length; i++) {

            if (i == crossIndex) {
                swap = !swap;
                index++;
                crossIndex = (index < points) ? crossoverPoints.get(index) : length;
            }

            if (swap) {
                Gene gene1 = dna1.getGene(i);
                Gene gene2 = dna2.getGene(i);
                Object value1 = gene1.getValue();
                gene1.setValue(gene2.getValue());
                gene2.setValue(value1);
            }
        }
    }

    private List<Integer> generateCrossoverPoints(final int length) {
        List<Integer> indices = new ArrayList<>(points);
        while (indices.size() < points) {
            final int index = ThreadLocalRandom.current().nextInt(1,length);
            if (!indices.contains(index))
                indices.add(index);
        }
        Collections.sort(indices);
        return indices;
    }

    public double getMatchProbability() {
        return matchProbability;
    }

    public void setMatchProbability(final double matchProbability) {
        probabilityFilter(matchProbability);
        this.matchProbability = matchProbability;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(final int points) {
        pointsFilter(points);
        this.points = points;
    }
}
