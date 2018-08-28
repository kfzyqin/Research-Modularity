package ga.operations.selectionOperators.selectors;

import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.Coupleable;
import ga.operations.selectionOperators.selectionSchemes.SelectionScheme;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a base implementation for Selector with Coupleable restrictions on individuals
 * and gender separation. To use the selector, one needs to provide a wrapper class that extends this
 * class for a particular selection scheme.
 *
 * @author Siu Kei Muk (David)
 * @since 29/09/16.
 */
public abstract class CoupleSelector<G extends Chromosome & Coupleable> implements Selector<G> {
    protected List<Individual<G>> maleIndividuals;
    protected List<Individual<G>> femaleIndividuals;
    protected List<Double> maleFitnessValues;
    protected List<Double> femaleFitnessValues;
    protected SelectionScheme scheme;

    public CoupleSelector(@NotNull final SelectionScheme scheme) {
        this.scheme = scheme;
        maleFitnessValues = new ArrayList<>();
        femaleFitnessValues = new ArrayList<>();
        maleIndividuals = new ArrayList<>();
        femaleIndividuals = new ArrayList<>();
    }

    @Override
    public List<G> select(final int numOfMates) {
        List<G> parents = new ArrayList<>(2);
        G male = maleIndividuals.get(scheme.select(maleFitnessValues)).getChromosome();
        G female = femaleIndividuals.get(scheme.select(femaleFitnessValues)).getChromosome();
        parents.add(male);
        parents.add(female);
        return parents;
    }

    @Override
    public void setSelectionData(@NotNull final List<Individual<G>> individuals) {
        maleIndividuals.clear();
        maleFitnessValues.clear();
        femaleIndividuals.clear();
        femaleFitnessValues.clear();

        for (int i = 0; i < individuals.size(); i++) {
            Individual<G> individual = individuals.get(i);
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
