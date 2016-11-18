package experiment1;

import com.sun.istack.internal.NotNull;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;

import java.util.concurrent.ThreadLocalRandom;

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
 * @author Siu Kei Muk (David)
 * @since 30/08/16.
 */
public class Exp1FitnessFunction implements FitnessFunction<SimpleMaterial> {

    private final int target;
    private final String targetBitString;

    public Exp1FitnessFunction() {
        this.target = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        targetBitString = Integer.toBinaryString(target);
    }

    public Exp1FitnessFunction(final int target) {
        this.target = target;
        targetBitString = Integer.toBinaryString(target);
    }

    public int getTarget() {
        return target;
    }

    public String getTargetBitString() {
        return targetBitString;
    }

    @Override
    public double evaluate(@NotNull final SimpleMaterial phenotype) {
        int num = 0;
        for (int i = 0; i < phenotype.getSize(); i++) {
            num = (num << 1) | (Integer) phenotype.getGene(i).getValue();
        }
        final int comparison = ~(num ^ target);
        return Integer.bitCount(comparison);
    }

    @Override
    public void update() {

    }
}
