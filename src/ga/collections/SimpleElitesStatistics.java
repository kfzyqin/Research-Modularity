package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a simple implementation of statistics bookkeeping that
 * keeps records of the best elite of each generation.
 *
 * @author Siu Kei Muk (David)
 * Created by david on 11/09/16.
 */
public class SimpleElitesStatistics<C extends Chromosome> implements Statistics<C> {

    private int generation;
    List<Individual<C>> elites;
    List<Double> deltas;

    public SimpleElitesStatistics() {
        generation = 0;
        elites = new ArrayList<>();
        deltas = new ArrayList<>();
    }

    /**
     * @param maxGen maximum number of generations, for efficiency reason
     */
    public SimpleElitesStatistics(final int maxGen) {
        generation = 0;
        elites = new ArrayList<>(maxGen);
        deltas = new ArrayList<>(maxGen);
    }

    private SimpleElitesStatistics(@NotNull final List<Individual<C>> elites,
                                   @NotNull final List<Double> deltas) {
        generation = elites.size();
        this.elites = new ArrayList<>(generation);
        this.deltas = new ArrayList<>(deltas);
        for (int i = 0; i < generation; i++)
            this.elites.add(elites.get(i).copy());
    }

    @Override
    public void record(@NotNull final List<Individual<C>> data) {
        Individual<C> elite = data.get(0).copy();
        elites.add(elite);
        if (generation == 0)
            deltas.add(elite.getFitness());
        else
            deltas.add(elite.getFitness() - elites.get(generation-1).getFitness());
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
        return elites.get(generation).getFitness();
    }

    @Override
    public SimpleElitesStatistics<C> copy() {
        return new SimpleElitesStatistics<>(elites, deltas);
    }

    @Override
    public String getSummary(final int generation) {
        return String.format("Generation: %d; Delta: %.4f, Best >> %s <<",
                generation, deltas.get(generation), elites.get(generation).toString());
    }
}
