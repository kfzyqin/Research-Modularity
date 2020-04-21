package ga.operations.selectionOperators.selectors;

import ga.collections.Individual;
import ga.components.chromosomes.Chromosome;
import ga.components.genes.DataGene;
import ga.components.materials.Material;
import ga.frame.states.State;
import ga.operations.selectionOperators.selectionSchemes.ExtendedTournamentScheme;
import ga.operations.selectionOperators.selectionSchemes.SelectionScheme;
import ga.operations.selectionOperators.selectionSchemes.SimpleTournamentScheme;
import ga.others.GeneralMethods;
import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.Data;
import java.util.*;

public class ExtendedTournamentSelector<C extends Chromosome> extends BaseSelector<C> {

    protected int[][] targets;
    protected int maxCycle;
    protected int size;
    private Random randomno = new Random();
    public Hashtable<Material, Map<Integer, Integer>> targetSolvedTable = new Hashtable<>();
    private final List<Integer> threshold;
    private int generation;

    public ExtendedTournamentSelector(@NotNull int size, int[][] targets, int maxCycle, List<Integer> threshold) {
        super(new SimpleTournamentScheme(size));
        this.size = size;
        this.targets = targets;
        this.maxCycle = maxCycle;
        this.threshold = threshold;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
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


    public int getNumberOfTargets(int generation) {
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

    private List<Individual<C>> getFullySolvedIndividuals(List<Individual<C>> individuals, int perturbationSize) {
        int targetNum = getNumberOfTargets(generation);
        List<Individual<C>> fullySolvedIndividuals = new ArrayList<>();

        for (Individual<C> individual : individuals) {
            int maxSolved = GeneralMethods.getCombinationNumber(targets[0].length, perturbationSize) * targetNum;
            if (!targetSolvedTable.containsKey(individual.getChromosome().getPhenotype(false))) {
                System.out.println("individual not in table");
            }
            if (!targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(perturbationSize)) {
                System.out.println("table has individual but do not have the perturbation size");
                System.out.println("perturbation size: " + perturbationSize);
                System.out.println("individual map in table: " + targetSolvedTable.get(individual.getChromosome().getPhenotype(false)));
            }
            int solved = targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(perturbationSize);
            if (solved == maxSolved) {
                fullySolvedIndividuals.add(individual);
            }
        }
        return fullySolvedIndividuals;
    }

    private Individual<C> getCurrentBestIndividual(List<Individual<C>> selectedIndividuals, int perturbationSize) {
        List<Individual<C>> currentBestIndividuals = getCurrentBestIndividuals(selectedIndividuals, perturbationSize);
        if (currentBestIndividuals.size() == 1) return currentBestIndividuals.get(0);
        else if (currentBestIndividuals.size() > 1) {

        }
        else if (currentBestIndividuals.size() == 0) {

        }
        return null;

    }

    private List<Individual<C>> getCurrentBestIndividuals(List<Individual<C>> selectedIndividuals, int perturbationSize) {
        Map<Individual<C>, Integer> individualCurrentSolved = new HashMap<>();
        List<Individual<C>> bestIndividuals = new ArrayList<>();
        for (Individual<C> individual : selectedIndividuals) {
            individualCurrentSolved.put(individual, targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(perturbationSize));
        }

        Integer currentBest = 0;
        for (Integer solvedNum : individualCurrentSolved.values()) {
            if (solvedNum > currentBest) currentBest = solvedNum;
        }

        for (Individual<C> individual : individualCurrentSolved.keySet()) {
            if (currentBest == individualCurrentSolved.get(individual)) {
                bestIndividuals.add(individual);
            }
        }
        return bestIndividuals;
    }


    private int selectIndividual() {
        //generate random individuals
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

        int targetNum = getNumberOfTargets(generation);
        int perturbationSize = 0;
        int selectedIndex = -1;

        while (perturbationSize < targets[0].length) {

            //update the table
            for (Individual<C> individual : selectedIndividuals) {
                if (targetSolvedTable.containsKey(individual.getChromosome().getPhenotype(false))) {
                    if (!hasUpdatedToPerturbationSize(individual, perturbationSize)) {
                        int[][] currentTargets = new int[targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(-1)][];
                        for (int i = 0; i < currentTargets.length; i++) {
                            currentTargets[i] = targets[i];
                        }

                        updatePerturbationSize(individual, perturbationSize, currentTargets);
                        if (!targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(perturbationSize)) {
                            System.out.println("after update: individual not update to perturbation size");
                            System.out.println("perturbation size: " + perturbationSize);
                            System.out.println("map: " + targetSolvedTable.get(individual.getChromosome().getPhenotype(false)));
                        }
                    }
                    if (!hasUpdatedToTargetSize(individual, targetNum)) {
                        int saved = targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(-1);
                        int[][] currentTargets = new int[targetNum - saved][];
                        for (int i = 0; i < currentTargets.length; i++) {
                            currentTargets[i] = targets[i + saved];
                        }
                        updateTargetSize(individual, perturbationSize, currentTargets);
                    }
                } else {
                    Map<Integer, Integer> targetNumMap = new HashMap<>();
                    targetNumMap.put(-1, targetNum);
                    targetSolvedTable.put(individual.getChromosome().getPhenotype(false), targetNumMap);
                    int[][] currentTargets = new int[targetNum][];
                    for (int i = 0; i < currentTargets.length; i++) {
                        currentTargets[i] = targets[i];
                    }
                    updatePerturbationSize(individual, perturbationSize, currentTargets);
                    updateTargetSize(individual, perturbationSize, currentTargets);
                }
            }

//            for (Individual<C> individual : selectedIndividuals) {
//                if (hasUpdatedTargetNum(individual, targetNum)) {//table has the individual and updated to current target number
//                    if (!targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(perturbationSize)) { //not update to the size
//                        int[][] currentTargets = new int[targetNum][];
//                        for (int i = 0; i < targetNum; i++) {
//                            currentTargets[i] = targets[i];
//                        }
//                        updateMap(individual, perturbationSize, currentTargets);
//                    }
//                } else updateTargetNum(individual, targetNum, perturbationSize);
//            }

//            if (perturbationSize == 0 && selectedIndividuals.size() == size) {
//                int solvedZeroPerturbation = 0;
//                for (Individual<C> individual : selectedIndividuals) {
//                    if (targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(0) != 0) solvedZeroPerturbation += 1;
//                }
//                if (solvedZeroPerturbation == 0) {
//                    Individual<C> randomChoice = selectedIndividuals.get(randomno.nextInt(selectedIndividuals.size()));
//                    for (Integer index : indices) {
//                        if (individuals.get(index).getChromosome().getPhenotype(false).equals(randomChoice.getChromosome().getPhenotype(false)))
//                            selectedIndex = index;
//                    }
//                    break;
//                }
//            }

            // choose the individual
            List<Individual<C>> fullySolvedIndividuals = getFullySolvedIndividuals(selectedIndividuals, perturbationSize);
//            List<Individual<C>> fullySolvedIndividuals = getCurrentBestIndividuals(selectedIndividuals, perturbationSize);
            if (fullySolvedIndividuals.size() == 1) {
                for (Integer index : indices) {
                    Individual<C> best = fullySolvedIndividuals.get(0);
                    if (individuals.get(index).getChromosome().getPhenotype(false).equals(best.getChromosome().getPhenotype(false)))
                        selectedIndex = index;
                }
                break;
            }
            else if (fullySolvedIndividuals.size() > 1) {
//                System.out.println("fullySolved > 1, fullySolvedSize: " + fullySolvedIndividuals.size());
//                System.out.println("perturbation size: " + perturbationSize);
                selectedIndividuals = fullySolvedIndividuals;
                if (perturbationSize == targets[0].length - 1) {
                    Individual<C> randomChoice = selectedIndividuals.get(randomno.nextInt(selectedIndividuals.size()));
                    for (Integer index : indices) {
                        if (individuals.get(index).getChromosome().getPhenotype(false).equals(randomChoice.getChromosome().getPhenotype(false)))
                            selectedIndex = index;
                    }
                    break;
                }
                else perturbationSize += 1;
            }
            else if (fullySolvedIndividuals.size() == 0) {
                //choose the best
//                Individual<C> currentBest = getCurrentBestIndividual(selectedIndividuals, perturbationSize);
                //randomly choose one
                List<Individual<C>> currentBestIndividuals = getCurrentBestIndividuals(selectedIndividuals, perturbationSize);
                Individual<C> currentBest = currentBestIndividuals.get(randomno.nextInt(currentBestIndividuals.size()));
                for (Integer index : indices) {
                    if (individuals.get(index).getChromosome().getPhenotype(false).equals(currentBest.getChromosome().getPhenotype(false)))
                        selectedIndex = index;
                }
                break;
            }
            else {
                break;
            }
        }
        return selectedIndex;

    }

    //second method update
    private boolean hasUpdatedToPerturbationSize(Individual<C> individual, int perturbationSize) {
        boolean updated = true;
        for (int size = 0; size <= perturbationSize; size ++) {
            if (!targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(size)) {
                updated = false;
            }
        }
        return updated;
    }

    private boolean hasUpdatedToTargetSize(Individual<C> individual, int targetNum) {
        if (targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(-1)) {
            return targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(-1) == targetNum;
        }
        else return false;
    }

    private void updatePerturbationSize(Individual<C> individual, int perturbationSize, int[][] currentTargets) {
        for (int size = 0; size <= perturbationSize; size ++) {
            if (!targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(size)) {
                for (int[] aTarget : currentTargets) {
                    DataGene[][] attractors = GeneralMethods.generateAttractors(aTarget, size);
                    int solvedPerturbations = 0;
                    for (DataGene[] perturbedTarget : attractors) {
                        if (individual.canRegulateToTarget(aTarget, perturbedTarget, maxCycle)) solvedPerturbations += 1;
                    }
                    boolean contains = targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(perturbationSize);
                    if (contains) {
                        targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).put(perturbationSize,
                                targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(perturbationSize) + solvedPerturbations);
                    } else targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).put(perturbationSize, solvedPerturbations);
                }
            }
        }

    }

    private void updateTargetSize(Individual<C> individual, int perturbationSize, int[][] targets) {
        for (int[] aTarget : targets) {
            DataGene[][] attractors = GeneralMethods.generateAttractors(aTarget, perturbationSize);
            int solvedPerturbations = 0;
            for (DataGene[] perturbedTarget : attractors) {
                if (individual.canRegulateToTarget(aTarget, perturbedTarget, maxCycle)) solvedPerturbations += 1;
            }
            boolean contains = targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(perturbationSize);
            if (contains) {
                targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).put(perturbationSize,
                        targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(perturbationSize) + solvedPerturbations);
            } else targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).put(perturbationSize, solvedPerturbations);
        }

    }


    //first method update
    private void updateTargetNum(Individual<C> individual, int targetNum, int perturbationSize) {
        if (targetSolvedTable.containsKey(individual.getChromosome().getPhenotype(false))) {
            int targetNumSaved = targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(-1);

            // update perturbation size
            if (targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(perturbationSize)) {
                int[][] previousTargets = new int[targetNum][];
                for (int i = 0; i < targetNumSaved; i ++) {
                    previousTargets[i] = targets[i];
                }
                updateMap(individual, perturbationSize, previousTargets);
            }
            // update target size
            int[][] currentTargets = new int[targetNum - targetNumSaved][];
            for (int i = 0; i < currentTargets.length; i ++) {
                currentTargets[i] = targets[i+targetNumSaved];
            }
            targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).put(-1, targetNum);
            updateMap(individual, perturbationSize, currentTargets);
        } else {
            Map<Integer, Integer> targetNumMap = new HashMap<>();
            targetNumMap.put(-1, targetNum);
            targetSolvedTable.put(individual.getChromosome().getPhenotype(false), targetNumMap);
            int[][] currentTargets = new int[targetNum][];
            for (int i = 0; i < targetNum; i ++) {
                currentTargets[i] = targets[i];
            }
            updateMap(individual, perturbationSize, currentTargets);
        }

    }

    private void updateMap(Individual<C> individual, int perturbationSize, int[][] currentTargets) {
        //check for perturbation size < current perturbation size
        for (int size = 0; size < perturbationSize; size ++) {
            if (!targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(size)) {
                updateMap(individual, size, currentTargets);
            }
        }

        for (int[] aTarget : currentTargets) {
            DataGene[][] attractors = GeneralMethods.generateAttractors(aTarget, perturbationSize);
            int solvedPerturbations = 0;
            for (DataGene[] perturbedTarget : attractors) {
                if (individual.canRegulateToTarget(aTarget, perturbedTarget, maxCycle)) solvedPerturbations += 1;
            }
            boolean contains = targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).containsKey(perturbationSize);
            if (contains) {
                targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).put(perturbationSize,
                        targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(perturbationSize) + solvedPerturbations);
            } else targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).put(perturbationSize, solvedPerturbations);
        }

    }

    // if not in the table: false; if not correct target number: false
    private boolean hasUpdatedTargetNum(Individual<C> individual, int targetNum) {
        boolean updated = true;
        if (targetSolvedTable.containsKey(individual.getChromosome().getPhenotype(false))) { //table has the individual
            if (targetSolvedTable.get(individual.getChromosome().getPhenotype(false)).get(-1) != targetNum) { //table value(map) updated to current target number
                updated = false;
            }
        } else { //table don't have the individual
            updated = false;
        }
        return updated;
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
