package ga.operations.selectors;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosome.Chromosome;
import ga.components.chromosome.Coupleable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 29/09/16.
 */
public abstract class CoupleSelector<T extends Chromosome & Coupleable> implements Selector<T>{

    protected List<Individual<T>> maleIndividuals;
    protected List<Individual<T>> femaleIndividuals;
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
    public List<T> select(final int numOfMates) {
        List<T> parents = new ArrayList<>(2);
        T male = maleIndividuals.get(scheme.select(maleFitnessValues)).getChromosome();
        T female = femaleIndividuals.get(scheme.select(femaleFitnessValues)).getChromosome();
        parents.add(male);
        parents.add(female);
        return parents;
    }
}
