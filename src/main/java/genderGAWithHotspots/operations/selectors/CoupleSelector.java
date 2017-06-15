package genderGAWithHotspots.operations.selectors;

import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.operations.selectionOperators.selectionSchemes.SelectionScheme;
import ga.operations.selectionOperators.selectors.Selector;
import genderGAWithHotspots.components.chromosomes.Coupleable;

import java.util.ArrayList;
import java.util.List;

/*
    GASEE is a Java-based genetic algorithm library for scientific exploration and experiment.
    Copyright 2016 Siu-Kei Muk

    This file is part of GASEE.

    GASEE is free library: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 2.1 of the License, or
    (at your option) any later version.

    GASEE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with GASEE.  If not, see <http://www.gnu.org/licenses/>.
 */

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
