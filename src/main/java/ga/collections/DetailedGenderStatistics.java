package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.Coupleable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenyueqin on 15/6/17.
 */
public class DetailedGenderStatistics<G extends Chromosome & Coupleable> implements Statistics<G> {

    private int generation;
    private String directoryPath = "generated-outputs";

    protected List<Individual<G>> maleElites;
    protected List<Individual<G>> femaleElites;
    protected List<Individual<G>> elites;

    protected List<Individual<G>> maleWorsts;
    protected List<Individual<G>> femaleWorsts;
    protected List<Individual<G>> worsts;

    protected List<Individual<G>> maleMedians;
    protected List<Individual<G>> femaleMedians;
    protected List<Individual<G>> medians;

    protected List<Double> maleMeans;
    protected List<Double> femaleMeans;
    protected List<Double> means;

    protected List<Double> maleDeltas;
    protected List<Double> femaleDeltas;
    protected List<Double> deltas;

    public DetailedGenderStatistics() {
        generation = 0;
        maleElites = new ArrayList<>();
        femaleElites = new ArrayList<>();
        elites = new ArrayList<>();

        maleWorsts = new ArrayList<>();
        femaleWorsts = new ArrayList<>();
        worsts = new ArrayList<>();

        maleMedians = new ArrayList<>();
        femaleMedians = new ArrayList<>();
        medians = new ArrayList<>();

        maleMeans = new ArrayList<>();
        femaleMeans = new ArrayList<>();
        means = new ArrayList<>();

        maleDeltas = new ArrayList<>();
        femaleDeltas = new ArrayList<>();
        deltas = new ArrayList<>();
    }

    /**
     * @param maxGen maximum number of generations, for efficiency reason
     */
    public DetailedGenderStatistics(final int maxGen) {
        generation = 0;

        maleElites = new ArrayList<>(maxGen);
        femaleElites = new ArrayList<>(maxGen);
        elites = new ArrayList<>(maxGen);

        maleWorsts = new ArrayList<>(maxGen);
        femaleWorsts = new ArrayList<>(maxGen);
        worsts = new ArrayList<>(maxGen);

        maleMedians = new ArrayList<>(maxGen);
        femaleMedians = new ArrayList<>(maxGen);
        medians = new ArrayList<>(maxGen);

        maleMeans = new ArrayList<>(maxGen);
        femaleMeans = new ArrayList<>(maxGen);
        means = new ArrayList<>(maxGen);

        maleDeltas = new ArrayList<>(maxGen);
        femaleDeltas = new ArrayList<>(maxGen);
        deltas = new ArrayList<>(maxGen);
    }

    private DetailedGenderStatistics(@NotNull final List<Individual<G>> maleElites,
                                     @NotNull final List<Individual<G>> femaleElites,
                                     @NotNull final List<Individual<G>> maleWorsts,
                                     @NotNull final List<Individual<G>> femaleWorsts,
                                     @NotNull final List<Individual<G>> maleMedians,
                                     @NotNull final List<Individual<G>> femaleMedians,
                                     @NotNull final List<Double> maleMeans,
                                     @NotNull final List<Double> femaleMeans,
                                     @NotNull final List<Double> maleDeltas,
                                     @NotNull final List<Double> femaleDeltas) {
        generation = maleElites.size();
        this.maleElites = new ArrayList<>(generation);
        this.femaleElites = new ArrayList<>(generation);
        this.elites = new ArrayList<>(generation);

        this.maleWorsts = new ArrayList<>(generation);
        this.femaleWorsts = new ArrayList<>(generation);
        this.worsts = new ArrayList<>(generation);

        this.maleMedians = new ArrayList<>(generation);
        this.femaleMedians = new ArrayList<>(generation);
        this.medians = new ArrayList<>(generation);

        this.maleMeans = new ArrayList<>(generation);
        this.femaleMeans = new ArrayList<>(generation);
        this.means = new ArrayList<>(generation);

        this.maleDeltas = new ArrayList<>(generation);
        this.femaleDeltas = new ArrayList<>(generation);
        this.deltas = new ArrayList<>(generation);

        for (int i=0; i<generation; i++) {
            this.maleElites.add(maleElites.get(i).copy());
            this.femaleElites.add(femaleElites.get(i).copy());
            this.elites.add(elites.get(i).copy());

            this.maleWorsts.add(maleWorsts.get(i).copy());
            this.femaleWorsts.add(femaleWorsts.get(i).copy());
            this.worsts.add(worsts.get(i).copy());

            this.maleMedians.add(maleMedians.get(i).copy());
            this.femaleMedians.add(femaleMedians.get(i).copy());
            this.medians.add(medians.get(i).copy());

            this.maleMeans.add(maleMeans.get(i));
            this.femaleMeans.add(femaleMeans.get(i));
            this.means.add(means.get(i));

            this.maleDeltas.add(maleDeltas.get(i));
            this.femaleDeltas.add(maleDeltas.get(i));
            this.deltas.add(deltas.get(i));
        }
    }

    @Override
    public DetailedGenderStatistics copy() {
        return new DetailedGenderStatistics<>(maleElites, femaleElites, maleWorsts, femaleWorsts,
                maleMedians, femaleMedians, maleMeans, femaleMeans, maleDeltas, femaleDeltas);
    }

    private List<Individual<G>> getIndividualsWithGender(List<Individual<G>> data, boolean isMasculine) {
        List<Individual<G>> rtn = new ArrayList<>();
        for (Individual<G> e : data) {
            if (e.getChromosome().isMasculine() == isMasculine) {
                rtn.add(e.copy());
            }
        }
        return rtn;
    }

    @Override
    public void record(List<Individual<G>> data) {
        this.recordOverallIndividuals(data);

        List<Individual<G>> maleIndividuals = getIndividualsWithGender(data, true);
        List<Individual<G>> femaleIndividuals = getIndividualsWithGender(data, false);

        Individual<G> maleElite = maleIndividuals.get(0).copy();
        Individual<G> femaleElite = femaleIndividuals.get(0).copy();
        maleElites.add(maleElite);
        femaleElites.add(femaleElite);

        Individual<G> maleWorst = maleIndividuals.get(maleIndividuals.size() - 1).copy();
        Individual<G> femaleWorst = femaleIndividuals.get(femaleIndividuals.size() - 1).copy();
        maleWorsts.add(maleWorst);
        femaleWorsts.add(femaleWorst);

        Individual<G> maleMedian = maleIndividuals.get(maleIndividuals.size() / 2).copy();
        Individual<G> femaleMedian = femaleIndividuals.get(femaleIndividuals.size() / 2).copy();
        maleMedians.add(maleMedian);
        femaleElites.add(femaleMedian);

        maleMeans.add(getAverageFitnessValueOfAPopulation(maleIndividuals));
        femaleMeans.add(getAverageFitnessValueOfAPopulation(femaleIndividuals));

        if (generation == 0) {
            maleDeltas.add(maleElite.getFitness());
            femaleDeltas.add(femaleElite.getFitness());
        } else {
            maleDeltas.add(maleElite.getFitness() - maleElites.get(generation-1).getFitness());
            femaleDeltas.add(femaleElite.getFitness() - femaleElites.get(generation-1).getFitness());
        }
    }

    private void recordOverallIndividuals(List<Individual<G>> data) {
        Individual<G> elite = data.get(0).copy();
        Individual<G> worst = data.get(data.size() - 1).copy();
        Individual<G> median = data.get(data.size() / 2).copy();
        double averageFitnessValue = this.getAverageFitnessValueOfAPopulation(data);
        elites.add(elite);
        worsts.add(worst);
        medians.add(median);
        means.add(averageFitnessValue);
        if (generation == 0)
            deltas.add(elite.getFitness());
        else
            deltas.add(elite.getFitness() - elites.get(generation-1).getFitness());
    }

    private double getAverageFitnessValueOfAPopulation(@NotNull final List<Individual<G>> data) {
        double fitnessValueSum = 0;
        for (Individual<G> individual : data) {
            fitnessValueSum += individual.getFitness();
        }
        return fitnessValueSum / data.size();
    }

    @Override
    public void save(String filename) {

    }

    @Override
    public void nextGeneration() {
        this.generation += 1;
    }

    @Override
    public double getOptimum(int generation) {
        return elites.get(generation).getFitness();
    }

    @Override
    public String getSummary(int generation) {
        return null;
    }
}
