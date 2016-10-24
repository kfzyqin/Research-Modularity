package experiment2;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.materials.Material;
import ga.frame.State;
import ga.operations.dynamicHandlers.DynamicHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 3/09/16.
 */
public class RyanAdditiveDominanceChange implements DynamicHandler<SimpleDiploid> {

    private double threshold;
    private int cycleLength;

    public RyanAdditiveDominanceChange(final double threshold, final int cycleLength) {
        filter(threshold, cycleLength);
        this.threshold = threshold;
        this.cycleLength = cycleLength;
    }

    private void filter(final double threshold, final int cycleLength) {
        if (threshold < 0 || threshold > 1)
            throw new IllegalArgumentException("Threshold must be between 0 and 1.");
        if (cycleLength < 1)
            throw new IllegalArgumentException("Cycle length must be at least 1.");
    }

    @Override
    public boolean handle(@NotNull State<SimpleDiploid> state) {
        final int currGen = state.getGeneration();
        if (currGen == 0 || currGen % cycleLength != 0)
            return false;
        state.getFitnessFunction().update();
        List<Double> preValues = extractFitnessValue(state.getPopulation().getIndividualsView());
        state.evaluate(false);
        List<Individual<SimpleDiploid>> individuals = state.getPopulation().getIndividualsView();
        List<Double> postValues = extractFitnessValue(individuals);
        final int length = individuals.get(0).getChromosome().getLength();

        for (int i = 0; i < preValues.size(); i++) {
            final double fitness1 = preValues.get(i);
            final double fitness2 = postValues.get(i);
            if (fitness1 - fitness2 > threshold*fitness1) {
                Material material1 = individuals.get(i).getChromosome().getMaterialsView().get(0);
                Material material2 = individuals.get(i).getChromosome().getMaterialsView().get(1);
                for (int j = 0; j < length; j++) {
                    RyanAdditiveSchemeGene gene1 = (RyanAdditiveSchemeGene) material1.getGene(j);
                    RyanAdditiveSchemeGene gene2 = (RyanAdditiveSchemeGene) material2.getGene(j);
                    if (toPoints(gene1.getValue()) + toPoints(gene2.getValue()) > 10) {
                        demote(gene1);
                        demote(gene2);
                    } else {
                        promote(gene1);
                        promote(gene2);
                    }
                }
            }
        }
        state.evaluate(true);
        return true;
    }

    private int toPoints(final char c) {
        switch (c) {
            case 'A':
                return 2;
            case 'B':
                return 3;
            case 'C':
                return 7;
            case 'D':
                return 9;
            default:
                return 0;
        }
    }

    private void promote(RyanAdditiveSchemeGene gene) {
        final char c = gene.getValue();
        switch (c) {
            case 'A':
                gene.setValue('B');
                return;
            case 'B':
                gene.setValue('C');
                return;
            case 'C':
                gene.setValue('D');
        }
    }

    private void demote(RyanAdditiveSchemeGene gene) {
        final char c = gene.getValue();
        switch (c) {
            case 'D':
                gene.setValue('C');
                return;
            case 'C':
                gene.setValue('B');
                return;
            case 'B':
                gene.setValue('A');
                return;
        }
    }

    private List<Double> extractFitnessValue(List<Individual<SimpleDiploid>> individuals) {
        final int size = individuals.size();
        List<Double> fitnessValues = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            fitnessValues.add(individuals.get(i).getFitness());
        }
        return fitnessValues;
    }
}
