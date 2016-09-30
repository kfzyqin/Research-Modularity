package ga.operations.selectors;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosome.Chromosome;
import ga.components.chromosome.Coupleable;

import java.util.List;

/**
 * Created by david on 29/09/16.
 */
public class SimpleTournamentCoupleSelector<T extends Chromosome & Coupleable> extends CoupleSelector<T>{

    public SimpleTournamentCoupleSelector(final int size, final int dominanceProbability) {
        super(new SimpleTournamentScheme(size, dominanceProbability));
    }

    @Override
    public void setSelectionData(@NotNull final List<Individual<T>> individuals) {
        maleIndividuals.clear();
        maleFitnessValues.clear();
        femaleIndividuals.clear();
        femaleFitnessValues.clear();

        for (int i = 0; i < individuals.size(); i++) {
            Individual<T> individual = individuals.get(i);
            if (individual.getChromosome().isMasculine()) {
                maleIndividuals.add(individual);
                maleFitnessValues.add(individual.getFitness());
            } else {
                femaleIndividuals.add(individual);
                femaleFitnessValues.add(individual.getFitness());
            }
        }
    }
}
