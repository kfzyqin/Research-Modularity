package examples.experiment1;

import au.com.bytecode.opencsv.CSVWriter;
import com.sun.istack.internal.NotNull;
import ga.collections.Individual;
import ga.collections.Statistics;
import ga.components.chromosomes.SimpleHaploid;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/*
    GASEE is a Java-based genetic algorithm library for scientific exploration and experiment.
    Copyright 2016 Siu-Kei Muk

    This file is part of GASEE.

    GASEE is free library: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 2.1 of the License, or
    (at your option) any later version.

    GASEE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with GASEE.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author Siu Kei Muk (David)
 * @since 31/08/16.
 */
public class Exp1Statistics implements Statistics<SimpleHaploid> {

    private int currentGen;
    private final List<Individual<SimpleHaploid>> elites;
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
     * and calculate the difference of best fitnessFunctions values between
     * successive generations.
     * @param data A descending sorted list of individuals by fitnessFunctions values.
     */
    @Override
    public void record(@NotNull List<Individual<SimpleHaploid>> data) {
        Individual<SimpleHaploid> elite = data.get(0).copy();

        if (currentGen < elites.size()) {
            elites.set(currentGen, elite);
            deltas.set(currentGen, elite.getFitness() - elites.get(currentGen-1).getFitness());
        } else {
            elites.add(elite);
            if (deltas.size() > 0) {
                deltas.add(elite.getFitness() - elites.get(currentGen - 1).getFitness());
            } else
                deltas.add(elite.getFitness());
        }
    }

    @Override
    public void print(final int generation) {
        if (generation >= deltas.size())
            return;
//        for (Individual<SimpleHaploid> e : elites) {
//            System.out.print(e.getFitness() + " ");
//        }
//        System.out.println();
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

    // @Override
    public double getDelta(final int generation) {
        return deltas.get(generation);
    }

    @Override
    public double getOptimum(final int generation) {
        return elites.get(generation).getFitness();
    }

    @Override
    public Statistics<SimpleHaploid> copy() {
        return null;
    }

    @Override
    public String getSummary(int generation) {
        return String.format("Generation: %d; Delta: %.4f, Best >> %s <<",
                generation, deltas.get(generation), elites.get(generation).toString());
    }

    public void generateCSVFile(String fileName) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(fileName), '\t');
        String[] entries = "Best#".split("#");
        writer.writeNext(entries);
        for (int i=0; i<=currentGen; i++) {
            entries = (Double.toString(elites.get(i).getFitness()) + "#").split("#");
            writer.writeNext(entries);
        }
        writer.close();
    }
}
