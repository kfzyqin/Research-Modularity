package experiments.experiment6;

import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.*;
import ga.others.GeneralMethods;
import ga.others.ModularityPathAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InterModulePathPlotter {
    private static final int[] target1 = {
            1, -1, 1, -1, 1,
            -1, 1, -1, 1, -1
    };
    private static final int[] target2 = {
            1, -1, 1, -1, 1,
            1, -1, 1, -1, 1
    };

    private static final int maxCycle = 30;

    private static final double perturbationRate = 0.15;
    private static final List<Integer> thresholds = Arrays.asList(0, 500);
    private static final int[] perturbationSizes = {0, 1, 2, 3, 4, 5, 6, 7};
    private static final double stride = 0.01;


    public static void main(String[] args) throws IOException {
        String targetPath = "/Users/qin/Portal/generated-outputs/record-zhenyue-balanced-combinations-p01";

        int[][] targets = {target1, target2};

//        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetric(
//                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

        FitnessFunction fitnessFunction = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                targets, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

        File[] directories = new File(targetPath).listFiles(File::isDirectory);

        for (File aDirectory : directories) {
            try {
                System.out.println("a directory: " + aDirectory);
                String aModFile = aDirectory + "/" + "phenotypes_fit.list";
                List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);
                String[] lastGRNString = lines.get(lines.size() - 1);
                SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lastGRNString);
                int interEdgeNo = GeneralMethods.getInterModuleEdgeNumber(GeneralMethods.convertStringArrayToIntArray(lastGRNString));

                if (interEdgeNo > 15) {
                    continue;
                }

                List<List<Double>> allPotentialPaths = new ArrayList<>();
                for (Integer anEdge : IntStream.range(0, interEdgeNo + 1).boxed().collect(Collectors.toList())) {
                    List<Double> removedEdgeFitnesses = ModularityPathAnalyzer.removeEdgeAnalyzer(anEdge, aMaterial,
                            fitnessFunction, true, 1700, null, false);

                    allPotentialPaths.add(removedEdgeFitnesses);
                }

                ProcessBuilder postPB = new ProcessBuilder("python2", "./python-tools/java_post_mate.py",
                        aDirectory.toString(), allPotentialPaths.toString());

                Process p = postPB.start();

                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String ret = in.readLine();
                System.out.println("value is : " + ret);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Array out of bound caught! ");
            }
        }
    }



}
