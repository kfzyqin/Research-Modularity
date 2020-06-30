package ga.operations.selectionOperators.selectors;

import ga.collections.DetailedStatistics;
import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.components.genes.DataGene;
import ga.components.materials.Material;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsCombinationWithResampleAsymmetric;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import ga.others.GeneralMethods;
import it.unimi.dsi.fastutil.Hash;
import org.apache.batik.dom.util.HashTable;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

public class ExtendedTournamentSelector<C extends Chromosome> extends BaseSelector<C> {

    protected int[][] targets;
    protected int maxCycle;
    public int size;
    private Random randomno = new Random();
    public Hashtable<Material, Map<Integer, Double>> targetSolvedTable = new Hashtable<>();
    public List<Integer> threshold;
    private int generation;
    private double k;

    public ExtendedTournamentSelector(@NotNull int size, int[][] targets, int maxCycle, List<Integer> threshold, double k) {
        super(new SimpleTournamentScheme(size));
        this.size = size;
        this.targets = targets;
        this.maxCycle = maxCycle;
        this.threshold = threshold;
        this.k = k;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public void setThreshold() {
//        if (targetNum == 2 && !targetadded) {
//            targetadded = true;
            List arrList = new ArrayList(threshold);
            arrList.add(generation);
            threshold = arrList;
//        }
//        return threshold;
    }

    /**
     *
     * @param individuals list of individuals of the current generation sorted in descending order
     */
    @Override
    public void setSelectionData(@NotNull List<Individual<C>> individuals) {

        fitnessValues.clear();
        this.individuals = individuals;
        for (int i = 0; i < individuals.size(); i++) {
            fitnessValues.add(individuals.get(i).getFitness());
        }

    }

    int targetNum = 1;

    // without k
    public int getTargetNum (int generation){
        if (threshold.size() == 1) return 1;
        int targets = 0;
        for (int targetNum = 0; targetNum < threshold.size(); targetNum ++) {
            if (targetNum < threshold.size() - 1) {
                if (generation >= threshold.get(targetNum) && generation < threshold.get(targetNum + 1)) {
                    targets = targetNum + 1;
                    break;
                } else {
                    continue;
                }
            }
            else if (targetNum == threshold.size()-1) {
                if (generation >= threshold.get(targetNum)) {
                    targets = targetNum + 1;
                }
            }
        }
        return targets;
    }

    // with k
    public void getNumberOfTargets(double k) {
        List<Double> fitness = new ArrayList<>();

        for (Individual<C> individual : individuals) {
            fitness.add(individual.getFitness());
        }

        double kproportion = GeneralMethods.kproportion(k, fitness);
        double best = GeneralMethods.best(fitness);
        if (kproportion == best && targetNum < targets.length) targetNum += 1;
    }




    boolean targetadded = false;
    boolean first = true;




    public int selectIndividual() {
        // generate random individuals
        List<Integer> indices = new ArrayList<>(size);
        List<Individual<C>> selectedIndividuals = new ArrayList<>();
        while (indices.size() < size) {
            final int index = randomno.nextInt(this.individuals.size());
            if (!indices.contains(index))
                indices.add(index);
        }
        for (Integer index : indices) {
            selectedIndividuals.add(this.individuals.get(index));
        }

//        targetNum = getTargetNum(generation);
        if (targetNum < targets.length) {
            getNumberOfTargets(k);
        }
        int perturbationSize = 0;
        int selectedIndex = -1;

        if (targetNum == 2 && first) {
            first = false;
            setThreshold();
            System.out.println("add 2 target"+threshold);
        }

        while (perturbationSize < targets[0].length) {
            // update table
            for (Individual<C> individual : selectedIndividuals) {
                if (targetSolvedTable.containsKey(individual.getChromosome().getPhenotype(false))) {
                    if (!hasUpdatedToPerturbationSize(individual, perturbationSize)) {
                        int[][] currentTargets = new int[targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(-1).intValue()][];
                        for (int i = 0; i < currentTargets.length; i++) {
                            currentTargets[i] = targets[i];
                        }
                        for (int[] aTarget: currentTargets) {
                            updateTargetSize(individual, perturbationSize, aTarget);
                        }
//                        if (!targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(perturbationSize)) {
//                            System.out.println("after update: individual not update to perturbation size");
//                            System.out.println("perturbation size: " + perturbationSize);
//                            System.out.println("map: " + targetSolvedTable.get(individual.getChromosome().getPhenotype(false)));
//                        }
                    }
                    if (!hasUpdatedToTargetSize(individual, targetNum)) {
                        int saved = targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(-1).intValue();
                        int[] addedTarget = targets[saved];

                        // update target value in the map
                        targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).put(-1, Double.valueOf(targetNum));
                        updateTargetSize(individual, perturbationSize, addedTarget);
                    }
                } else { // map don't have individual
                    Map<Integer, Double> targetNumMap = new HashMap<>();
                    targetNumMap.put(-1, Double.valueOf(targetNum));
                    targetSolvedTable.put(individual.getChromosome().getPhenotype(false), targetNumMap);
                    int[][] currentTargets = new int[targetNum][];
                    for (int i = 0; i < currentTargets.length; i++) {
                        currentTargets[i] = targets[i];
                    }
                    for (int[] aTarget: currentTargets) {
                        updateTargetSize(individual, perturbationSize, aTarget);
                    }
                }
            }

            // select individual
            List<Individual<C>> bestIndividuals = getBestIndividuals(selectedIndividuals, perturbationSize);
            if (bestIndividuals.size() == 1) {
                for (Integer index : indices) {
                    Individual<C> best = bestIndividuals.get(0);
                    if (individuals.get(index).getChromosome().getPhenotype(false).equals(best.getChromosome().getPhenotype(false)))
                        selectedIndex = index;
                }
                break;
            }
            else if (bestIndividuals.size() > 1) {
//                System.out.println("fullySolved > 1, fullySolvedSize: " + fullySolvedIndividuals.size());
//                System.out.println("perturbation size: " + perturbationSize);
                selectedIndividuals = bestIndividuals;
                if (perturbationSize == targets[0].length-1) {
                    Individual<C> randomChoice = selectedIndividuals.get(randomno.nextInt(selectedIndividuals.size()));
                    for (Integer index : indices) {
                        if (individuals.get(index).getChromosome().getPhenotype(false).equals(randomChoice.getChromosome().getPhenotype(false)))
                            selectedIndex = index;
                    }
                    break;
                }
                else perturbationSize += 1;
            }
            else {
                break;
            }

        }

        Individual<C> selected = individuals.get(selectedIndex);
        if (targetSolvedTable.get(selected.getChromosome().getPhenotype(false)).containsKey(-2)) {
            targetSolvedTable.get(selected.getChromosome().getPhenotype(false)).put(-2,
                    Double.valueOf(1) + targetSolvedTable.get(selected.getChromosome().getPhenotype(false)).get(-2));
        } else targetSolvedTable.get(selected.getChromosome().getPhenotype(false)).put(-2, Double.valueOf(1));

//        if (generation == 599) {
//            try {
//                generateSomeGenerations();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        return selectedIndex;
    }

    private List<Individual<C>> getBestIndividuals(List<Individual<C>> selectedIndividuals, int perturbation) {
        List<Individual<C>> selection = new ArrayList<>();
        double gamma_avg = 0;
        for (Individual<C> individual : selectedIndividuals) {
            if (gamma_avg < targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(perturbation)) {
                gamma_avg = targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(perturbation);
            }
        }

        for (Individual<C> individual : selectedIndividuals) {
            if (targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(perturbation) == gamma_avg) {
                selection.add(individual);
            }
        }


        return selection;

    }

    //the perturbation size is in table
    private boolean hasUpdatedToPerturbationSize(Individual<C> individual, int perturbationSize) {
        return targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(perturbationSize);
    }

    private void updateTargetSize(Individual<C> individual, int perturbationSize, int[] addedTarget) {
        int combinations = GeneralMethods.getCombinationNumber(targets[0].length, perturbationSize);

        DataGene[][] perturbs = GeneralMethods.generatePurterbed(addedTarget, perturbationSize);
        List<Double> gammas = new ArrayList<>();

        for (DataGene[] perturb: perturbs) {
            if (individual.canAchieveAttractor(perturb, maxCycle)) {
                DataGene[] attractor = individual.getAttractor(perturb, maxCycle);
                double distance = getHammingDistance(attractor, addedTarget);
                double aD = (1 - (distance / ((double) addedTarget.length)));
                double oneGamma = Math.pow(aD, 5);
                gammas.add(oneGamma);
            }
        }
        double sum = GeneralMethods.getSum(gammas);
        double average = sum / combinations;
        if (targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(perturbationSize)) {
            double attained = targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(perturbationSize);
            double avg = (attained + average) / targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(-1);
            targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).put(perturbationSize, avg);

        } else {
            targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).put(perturbationSize, average);
        }

    }

    private boolean hasUpdatedToTargetSize(Individual<C> individual, int targetNum) {
        if (targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(-1)) {
            return targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(-1).intValue() == targetNum;
        }
        else return false;
    }

    protected double getHammingDistance(DataGene[] attractor, int[] target) {
        int count = 0;
        for (int i=0; i<attractor.length; i++) {
            if (attractor[i].getValue() == target[i]) {
                count += 1;
            }
        }
        return attractor.length - count;
    }

    String targetPath = "/Users/rouyijin/Desktop/modularity/Research-Modularity/generated-outputs/crossover25-mutation0.3";

    public void generateSomeGenerations() throws IOException{

        BufferedWriter pheOutputWriter;
        String aFileName = targetPath + "_gen_" + ((Integer) generation).toString() + ".maps";
        pheOutputWriter = new BufferedWriter(new FileWriter(aFileName));
        Set set = targetSolvedTable.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            pheOutputWriter.write(entry.getKey().toString() + " = " + entry.getValue().toString());
            pheOutputWriter.newLine();
        }

        pheOutputWriter.close();

    }



    @Override
    public List<C> select(int numOfMates) {
        Set<Integer> set = new HashSet<>(numOfMates);
        while (set.size() < numOfMates) {
            set.add(selectIndividual());
        }

        List<C> parents = new ArrayList<>(numOfMates);

        for (int i : set) {
            parents.add(individuals.get(i).getChromosome());
        }
        return parents;
    }

}
