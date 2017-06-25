package ga.operations.fitnessFunctions;

import com.sun.istack.internal.NotNull;
import ga.components.genes.DataGene;
import ga.components.materials.SimpleMaterial;
import ga.others.GeneralMethods;

import javax.xml.crypto.Data;
import java.util.*;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class GRNFitnessFunctionSingleTarget extends GRNFitnessFunction<SimpleMaterial> {
    private final int[] target;

    public GRNFitnessFunctionSingleTarget(int[] target, int maxCycle, int perturbations, double perturbationRate) {
        super(maxCycle, perturbations, perturbationRate);
        this.target = target;
    }

    public double evaluateAllPotentialAttractors(@NotNull SimpleMaterial phenotype) {
        Set<Double> fitnessValueSet = new HashSet<>();
        Set<DataGene[]> attractorSet = new HashSet<>();

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(-1);
        Set<List<Integer>> allPermutations = GeneralMethods.getAllLists(set, 10, 10);
        DataGene[][] startAttractors = new DataGene[(int) Math.pow(2, this.target.length)][this.target.length];
        int outer = 0;
        int inner = 0;
        for (List<Integer> list : allPermutations) {
            for (Integer element : list) {
                startAttractors[outer][inner] = new DataGene(element);
                inner++;
                inner %= target.length;
            }
            outer += 1;
        }
        int notMaxedFitness = 0;
        double fitnessValue = 0;
        for (DataGene[] startAttractor : startAttractors) {
            DataGene[] currentAttractor = startAttractor;
            int currentRound = 0;
            boolean isNotStable;
            do {
                DataGene[] updatedState = this.updateState(currentAttractor, phenotype, this.target);
                isNotStable = this.hasNotAttainedAttractor(currentAttractor, updatedState);
                currentAttractor = updatedState;
                currentRound += 1;
            } while (currentRound < this.maxCycle && isNotStable);

            if (currentRound < maxCycle) {
                int hammingDistance = this.getHammingDistance(currentAttractor, this.target);
                if (hammingDistance != 0) {
                    notMaxedFitness += 1;
                }
                double thisFitness = Math.pow((1 - (hammingDistance / ((double) this.target.length))), 5);
                fitnessValue += thisFitness;
                fitnessValueSet.add(thisFitness);
            } else {
                fitnessValue += 0;
            }
            attractorSet.add(currentAttractor);
        }
        Set<List<Integer>> anotherAttractorSet = new HashSet<>();
        System.out.println("not maxed fitness: " + notMaxedFitness);
        System.out.println("single fitness value: " + fitnessValue);
        System.out.println("no of fitness values: " + fitnessValueSet.size());
        for (DataGene[] attractor : attractorSet) {
            List<Integer> tmpList = new ArrayList<>();
            for (DataGene gene : attractor) {
                tmpList.add(gene.getValue());
            }
            anotherAttractorSet.add(tmpList);
        }
        System.out.println("no of attractors: " + anotherAttractorSet.size());
        System.out.println();
        double arithmeticMean = fitnessValue / this.perturbations;
        double networkFitness = 1 - Math.pow(Math.E, (-3 * arithmeticMean));
        return networkFitness;
    }

    @Override
    public double evaluate(@NotNull SimpleMaterial phenotype) {
        DataGene[][] startAttractors = this.generateInitialAttractors(this.perturbations, this.perturbationRate, this.target);
        double fitnessValue = 0;
        for (DataGene[] startAttractor : startAttractors) {
            DataGene[] currentAttractor = startAttractor;
            int currentRound = 0;
            boolean isNotStable;
            do {
                DataGene[] updatedState = this.updateState(currentAttractor, phenotype, this.target);
                isNotStable = this.hasNotAttainedAttractor(currentAttractor, updatedState);
                currentAttractor = updatedState;
                currentRound += 1;
            } while (currentRound < this.maxCycle && isNotStable);

            if (currentRound < maxCycle) {
                int hammingDistance = this.getHammingDistance(currentAttractor, this.target);
                double thisFitness = Math.pow((1 - (hammingDistance / ((double) this.target.length))), 5);
                fitnessValue += thisFitness;
            } else {
                fitnessValue += 0;
            }
        }
        double arithmeticMean = fitnessValue / this.perturbations;
        double networkFitness = 1 - Math.pow(Math.E, (-3 * arithmeticMean));
        return networkFitness;

//        int ones = 0;
//        for (int i=0; i<phenotype.getSize(); i++) {
//            int aPosition = (Integer) phenotype.getGene(i).getValue();
//            if (aPosition == 1) {
//                ones += 1;
//            }
//        }
//        return ones;
    }

    @Override
    public void update() {

    }
}
