package genderGAWithHotspots.operations.selectors;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.operations.selectionOperators.selectionSchemes.SelectionScheme;
import ga.operations.selectionOperators.selectors.Selector;
import genderGAWithHotspots.components.chromosomes.Coupleable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 29/09/16.
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
}
