package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;
import ga.components.chromosome.Coupleable;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 30/09/16.
 */
public class SimpleGenderElitesStatistics<G extends Chromosome & Coupleable> implements Statistics<G>{



    private int generation;
    protected List<Individual<G>> maleElites;
    protected List<Individual<G>> femaleElites;
    protected List<Double> femaleDeltas;
    protected List<Double> maleDeltas;

    public SimpleGenderElitesStatistics() {
        generation = 0;
        maleElites = new ArrayList<>();
        femaleElites = new ArrayList<>();
        maleDeltas = new ArrayList<>();
        femaleDeltas = new ArrayList<>();
    }

    /**
     * @param maxGen maximum number of generations, for efficiency reason
     */
    public SimpleGenderElitesStatistics(final int maxGen) {
        generation = 0;
        maleElites = new ArrayList<>(maxGen);
        femaleElites = new ArrayList<>(maxGen);
        maleDeltas = new ArrayList<>(maxGen);
        femaleDeltas = new ArrayList<>(maxGen);
    }

    private SimpleGenderElitesStatistics(@NotNull final List<Individual<G>> maleElites,
                                         @NotNull final List<Individual<G>> femaleElites,
                                         @NotNull final List<Double> maleDeltas,
                                         @NotNull final List<Double> femaleDeltas) {
        generation = maleElites.size();
        this.maleElites = new ArrayList<>(generation);
        this.femaleElites = new ArrayList<>(generation);

        this.maleDeltas = new ArrayList<>(maleDeltas);
        this.femaleDeltas = new ArrayList<>(femaleDeltas);
        for (int i = 0; i < generation; i++) {
            this.maleElites.add(maleElites.get(i).copy());
            this.femaleElites.add(femaleElites.get(i).copy());
        }
    }

    @Override
    public void record(@NotNull final List<Individual<G>> data) {
        Individual<G> maleElite = null;
        Individual<G> femaleElite = null;
        Individual<G> elite;
        boolean maleEliteExtracted = false;
        boolean femaleEliteExtracted = false;
        int index = 0;
        while (!maleEliteExtracted || !femaleEliteExtracted) {
            elite = data.get(index++);
            if (elite.getChromosome().isMasculine() && !maleEliteExtracted) {
                maleElite = elite.copy();
                maleEliteExtracted = true;
            }  else if (!elite.getChromosome().isMasculine() && !femaleEliteExtracted) {
                femaleElite = elite.copy();
                femaleEliteExtracted = true;
            }
        }
        maleElites.add(maleElite);
        femaleElites.add(femaleElite);
        if (generation == 0) {
            maleDeltas.add(maleElite.getFitness());
            femaleDeltas.add(femaleElite.getFitness());
        } else {
            maleDeltas.add(maleElite.getFitness() - maleElites.get(generation-1).getFitness());
            femaleDeltas.add(femaleElite.getFitness() - femaleElites.get(generation-1).getFitness());
        }
    }

    @Override
    public void save(@NotNull final String filename) {
        final File file = new File(filename);
        PrintWriter pw = null;
        try {
            file.createNewFile();
            pw = new PrintWriter(file);
            for (int i = 0; i <= generation; i++){
                pw.println(getSummary(i));
                pw.println();
            }

        } catch (IOException e) {
            System.err.println("Failed to save file.");
        } finally {
            if (pw != null)
                pw.close();
        }
    }

    @Override
    public void nextGeneration() {
        generation++;
    }

    @Override
    public double getOptimum(final int generation) {
        final double femaleOptimum = femaleElites.get(generation).getFitness();
        final double maleOptimum = maleElites.get(generation).getFitness();
        return (femaleOptimum > maleOptimum) ? femaleOptimum : maleOptimum;
    }

    @Override
    public SimpleGenderElitesStatistics<G> copy() {
        return new SimpleGenderElitesStatistics<>(maleElites, femaleElites, maleDeltas, femaleDeltas);
    }

    @Override
    public String getSummary(final int generation) {
        return String.format("Generation: %d;\nMale: Delta - %.4f;\n      Best >> %s <<\nFemale: Delta - %.4f\n      Best >> %s <<\n\n",
                generation,
                maleDeltas.get(generation), maleElites.get(generation).toString(),
                femaleDeltas.get(generation), femaleElites.get(generation));
    }
}
