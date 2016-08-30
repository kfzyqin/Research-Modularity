package experiment1;

import com.sun.istack.internal.NotNull;
import ga.collections.Statistics;
import ga.components.SequentialHaploid;
import ga.components.Individual;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 29/08/16.
 */
public class Exp1Statistics implements Statistics<SequentialHaploid> {

    private int generation = 0;
    private List<Individual<SequentialHaploid>> elites;
    private List<Double> deltas;

    public Exp1Statistics() {
        elites = new ArrayList<>();
        deltas = new ArrayList<>();
    }

    @Override
    public void record(Map<String, Object> data) {
        if (data.keySet().contains(MessageKeys.ELITE.key)) {
            generation++;
            Individual<SequentialHaploid> elite = (Individual<SequentialHaploid>) data.get(MessageKeys.ELITE.key);
            elites.add(elite);
            if (generation == 1)
                deltas.add(elite.getFitness());
            else
                deltas.add(elite.getFitness() - deltas.get(deltas.size()-1));
        }
    }

    @Override
    public void request(final int generation,
                        @NotNull final List<String> keys,
                        @NotNull final Map<String, Object> data) {

        if (generation > this.generation || generation < 0)
            return;

        if (keys.contains(MessageKeys.ELITE.key)) {
            if (generation == 0)
                data.put(MessageKeys.ELITE.key, Collections.unmodifiableList(elites));
            else
                data.put(MessageKeys.ELITE.key, elites.get(generation - 1));
        }

        if (keys.contains(MessageKeys.DELTA.key)) {
            if (generation == 0)
                data.put(MessageKeys.DELTA.key, Collections.unmodifiableList(deltas));
            else
                data.put(MessageKeys.DELTA.key, deltas.get(generation - 1));
        }
    }

    @Override
    public void save(final String filename) {
        final File file = new File(filename);
        PrintWriter pw = null;
        try {
            file.createNewFile();
            pw = new PrintWriter(file);
            for (int i = 1; i <= generation; i++){
                pw.printf("Generation: %d; Delta: %.4f, Best >> %s <<",
                          i, deltas.get(i-1), elites.get(i-1).toString());
            }

        } catch (IOException e) {
            System.err.println("Failed to save file.");
        } finally {
            if (pw != null)
                pw.close();
        }
    }

    /*
    @Override
    public void load(String filename) {

    }
    */
}
