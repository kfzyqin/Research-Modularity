package ga.others;

import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinations;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ModularityPathAnalyzer {
    public static List<Double> removeEdgeAnalyzer(int removalNumber, SimpleMaterial material,
                                                  FitnessFunction grnFit, boolean toCheat, int startGeneration) {
        List<Double> fitnessValues = new ArrayList<>();

        Set<Integer> interModuleEdges = new HashSet<>();

        int nodeSize = (int) Math.sqrt(material.getSize());
        for (int i=0; i<nodeSize; i++) {
            for (int j=0; j<nodeSize; j++) {
                if (toCheat) {
                    if (!((i < nodeSize / 2 && j < nodeSize / 2) || (i >= nodeSize / 2 && j >= nodeSize / 2))) {
                        if ((int) (material.getGene(i * nodeSize + j).getValue()) != 0) {
                            interModuleEdges.add(i * nodeSize + j);
                        }
                    }
                } else {
                    if ((int) (material.getGene(i*nodeSize + j).getValue()) != 0) {
                        interModuleEdges.add(i * nodeSize + j);
                    }
                }
            }
        }

        Integer[] interModuleEdgeArray = interModuleEdges.toArray(new Integer[interModuleEdges.size()]);

        List<List<Integer>> combinations = GeneralMethods.getCombination(interModuleEdgeArray, removalNumber);

        for (List<Integer> aCombination : combinations) {
            SimpleMaterial tmpMaterial = material.copy();
            for (Integer anEntry : aCombination) {
                tmpMaterial.getGene(anEntry).setValue(0);
            }
            fitnessValues.add(((GRNFitnessFunctionMultipleTargetsAllCombinations) grnFit).evaluate(tmpMaterial, startGeneration));
        }
        Collections.sort(fitnessValues);
        List<Double> topValues = fitnessValues.subList(Math.max((int) (fitnessValues.size() * 0.9), 0), fitnessValues.size());
        return topValues;
    }

    public static List<List<Double>> getAllPotentialPaths(String fileDirectorPath, FitnessFunction grnFit, boolean toCheat, int startGeneration) {
        List<List<Double>> allPotentialPaths = new ArrayList<>();

        String beforeConversionPath = fileDirectorPath + File.separator + "least_modular_phenotype.phe";
        String afterConversionPath = fileDirectorPath + File.separator + "converted_least_modular_phenotype.phe";

        String[] beforeLeastModularPhenotypeString = GeneralMethods.readFileLineByLine(beforeConversionPath).get(0);
        SimpleMaterial beforeLeastModularPhenotype = GeneralMethods.convertStringArrayToSimpleMaterial(beforeLeastModularPhenotypeString);

        String[] afterLeastModularPhenotypeString = GeneralMethods.readFileLineByLine(afterConversionPath).get(0);
        SimpleMaterial afterLeastModularPhenotype = GeneralMethods.convertStringArrayToSimpleMaterial(afterLeastModularPhenotypeString);

        int reducedEdgeNumber = GeneralMethods.getEdgeNumber(beforeLeastModularPhenotype) - GeneralMethods.getEdgeNumber(afterLeastModularPhenotype);

        for (Integer anIncreasedIndex : IntStream.range(0, reducedEdgeNumber+1).boxed().collect(Collectors.toList())) {
            allPotentialPaths.add(removeEdgeAnalyzer(anIncreasedIndex, beforeLeastModularPhenotype, grnFit, toCheat, startGeneration));
        }
        return allPotentialPaths;
    }
}
