package genderGAWithHotspots.operations.priorOperators;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.operations.priorOperators.PriorOperator;
import genderGAWithHotspots.components.chromosomes.Coupleable;

import java.util.List;

/**
 * Created by david on 9/10/16.
 */
public class SimpleGenderElitismOperator<G extends Chromosome & Coupleable> implements PriorOperator<G> {

    private int numOfElitesPerGender;

    public SimpleGenderElitismOperator(final int numOfElitesPerGender) {
        filter(numOfElitesPerGender);
        this.numOfElitesPerGender = numOfElitesPerGender;
    }

    private void filter(final int numOfElitesPerGender) {
        if (numOfElitesPerGender < 1)
            throw new IllegalArgumentException("Number of elites must be a positive integer.");
    }

    @Override
    public void preOperate(@NotNull final Population<G> population) {
        if (numOfElitesPerGender > population.getSize()/2) return;
        List<Individual<G>> individuals = population.getIndividualsView();
        int maleCount = 0;
        int femaleCount = 0;
        Individual<G> individual;
        for (int i = 0; i < individuals.size(); i++) {
            individual = individuals.get(i);
            if (maleCount < numOfElitesPerGender && individual.getChromosome().isMasculine()) {
                population.addCandidate(individual.copy());
                population.markSurvivedIndex(i);
                maleCount++;
            } else if (femaleCount < numOfElitesPerGender && !individual.getChromosome().isMasculine()) {
                population.addCandidate(individual.copy());
                population.markSurvivedIndex(i);
                femaleCount++;
            }
            if (femaleCount >= numOfElitesPerGender && maleCount >= numOfElitesPerGender) return;
        }
    }
}
