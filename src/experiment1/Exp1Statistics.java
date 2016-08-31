package experiment1;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.Individual;
import ga.components.SequentialHaploid;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 31/08/16.
 */
public class Exp1Statistics implements Statistics<SequentialHaploid> {

    private int currentGen;
    private final List<Individual<SequentialHaploid>> elites;
    private final List<Double> deltas;

    public Exp1Statistics() {
        currentGen = 0;
        elites = new ArrayList<>();
        deltas = new ArrayList<>();
    }

    public Exp1Statistics(final int maxGen) {
        currentGen = 0;
        elites = new ArrayList<>(maxGen);
        deltas = new ArrayList<>(maxGen);
    }

    /**
     * This method records the elite of a generation into "elites",
     * and calculate the difference of best fitness values between
     * successive generations.
     * @param data A descending sorted list of individuals by fitness values.
     */
    @Override
    public void record(@NotNull List<Individual<SequentialHaploid>> data) {
        Individual<SequentialHaploid> elite = data.get(0);

        if (currentGen < elites.size()) {
            elites.set(currentGen, elite);
            deltas.set(currentGen, elite.getFitness() - elites.get(currentGen-1).getFitness());
        } else {
            elites.add(elite);
            if (deltas.size() > 0)
                deltas.add(elite.getFitness() - elites.get(currentGen-1).getFitness());
            else
                deltas.add(elite.getFitness());
        }
    }

    @Override
    public void print(final int generation) {
        if (generation >= deltas.size())
            return;
        System.out.printf("Generation: %d; Delta: %.4f, Best >> %s <<\n",
                          generation, deltas.get(generation), elites.get(generation).toString());
    }

    @Override
    public void save(@NotNull String filename) {
        final File file = new File(filename);
        PrintWriter pw = null;
        try {
            file.createNewFile();
            pw = new PrintWriter(file);
            for (int i = 0; i <= currentGen; i++){
                pw.printf("Generation: %d; Delta: %.4f, Best >> %s <<\n",
                          i, deltas.get(i), elites.get(i).toString());
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
        currentGen++;
    }
}
