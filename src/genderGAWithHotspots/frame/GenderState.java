package genderGAWithHotspots.frame;

import com.sun.istack.internal.NotNull;
import ga.frame.State;
import ga.operations.mutators.Mutator;
import genderGAWithHotspots.collections.GenderPopulation;
import ga.components.chromosomes.Chromosome;
import genderGAWithHotspots.components.chromosomes.Coupleable;
import ga.operations.fitnessFunctions.FitnessFunction;
import genderGAWithHotspots.operations.hotspotMutators.HotspotMutator;
import genderGAWithHotspots.operations.reproducers.CoupleReproducer;
import genderGAWithHotspots.operations.selectors.CoupleSelector;

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
 *
 *
 * Created by david on 9/10/16.
 */
public abstract class GenderState<G extends Chromosome & Coupleable<H>, H> extends State<G> {

    protected HotspotMutator<H> hotspotMutator;

    public GenderState(@NotNull GenderPopulation<G> population,
                       @NotNull FitnessFunction fitnessFunction,
                       @NotNull Mutator mutator,
                       @NotNull CoupleReproducer<G> recombinator,
                       @NotNull CoupleSelector<G> selector,
                       HotspotMutator<H> hotspotMutator) {
        super(population, fitnessFunction, mutator, recombinator, selector, 2);
        this.hotspotMutator = hotspotMutator;
    }

    public abstract void mutateHotspots();
}
