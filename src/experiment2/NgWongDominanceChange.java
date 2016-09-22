package experiment2;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosome.SimpleDiploid;
import ga.components.materials.GeneticMaterial;
import ga.frame.GAState;
import ga.operations.dynamicHandler.DynamicHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 3/09/16.
 */
public class NgWongDominanceChange implements DynamicHandler<SimpleDiploid>{

    private double threshold;
    private int cycleLength;

    public NgWongDominanceChange(final double threshold, final int cycleLength) {
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
    public boolean handle(@NotNull final GAState<SimpleDiploid> state) {
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
                GeneticMaterial material1 = individuals.get(i).getChromosome().getMaterialsView().get(0);
                GeneticMaterial material2 = individuals.get(i).getChromosome().getMaterialsView().get(1);
                for (int j = 0; j < length; j++) {
                    NgWongSchemeGene gene1 = (NgWongSchemeGene) material1.getGene(j);
                    NgWongSchemeGene gene2 = (NgWongSchemeGene) material2.getGene(j);
                    dominanceChange(gene1);
                    dominanceChange(gene2);
                }
            }
        }
        state.evaluate(true);
        return true;
    }

    private void dominanceChange(NgWongSchemeGene gene) {
        char c = gene.getValue();
        switch (c) {
            case 'i':
                gene.setValue('1');
                return;
            case '1':
                gene.setValue('i');
                return;
            case 'o':
                gene.setValue('0');
                return;
            case '0':
                gene.setValue('o');
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
