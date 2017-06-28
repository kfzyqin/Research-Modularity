package ga.collections;

import au.com.bytecode.opencsv.CSVWriter;
import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a simple implementation of statistics bookkeeping that
 * keeps records of the best, worst and median individual, also the mean fitness of each generation.
 *
 * Created by Zhenyue Qin on 10/06/2017.
 * The Australian National University.
 */
public class DetailedStatistics <C extends Chromosome> implements Statistics<C> {

    private int generation;
    private String directoryPath = "generated-outputs";
    List<Individual<C>> elites; // best individuals
    List<Individual<C>> worsts;  // worst individuals
    List<Individual<C>> medians;  // median individuals
    List<Double> means; // mean fitness values
    List<Double> deltas;

    public DetailedStatistics() {
        generation = 0;
        elites = new ArrayList<>();
        worsts = new ArrayList<>();
        medians = new ArrayList<>();
        means = new ArrayList<>();
        deltas = new ArrayList<>();
    }

    /**
     * @param maxGen maximum number of generations, for efficiency reason
     */
    public DetailedStatistics(final int maxGen) {
        generation = 0;
        elites = new ArrayList<>(maxGen);
        worsts = new ArrayList<>(maxGen);
        medians = new ArrayList<>(maxGen);
        means = new ArrayList<>(maxGen);
        deltas = new ArrayList<>(maxGen);
    }

    private DetailedStatistics(@NotNull final List<Individual<C>> elites,
                               @NotNull final List<Individual<C>> worsts,
                               @NotNull final List<Individual<C>> medians,
                               @NotNull final List<Double> means,
                               @NotNull final List<Double> deltas) {
        generation = elites.size();
        this.elites = new ArrayList<>(generation);
        this.means = new ArrayList<>(means);
        this.deltas = new ArrayList<>(deltas);
        for (int i = 0; i < generation; i++) {
            this.elites.add(elites.get(i).copy());
            this.worsts.add(worsts.get(i).copy());
            this.medians.add(medians.get(i).copy());
            this.means.add(means.get(i));
        }
    }

    @Override
    public DetailedStatistics<C> copy() {
        return new DetailedStatistics<>(elites, worsts, medians, means, deltas);
    }

    private double getAverageFitnessValueOfAPopulation(@NotNull final List<Individual<C>> data) {
        double fitnessValueSum = 0;
        for (Individual<C> individual : data) {
            fitnessValueSum += individual.getFitness();
        }
        return fitnessValueSum / data.size();
    }

    @Override
    public void record(List<Individual<C>> data) {
        Individual<C> elite = data.get(0).copy();
        Individual<C> worst = data.get(data.size() - 1).copy();
        Individual<C> median = data.get(data.size() / 2).copy();
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

    public void setDirectory(@NotNull String directoryName) {
        this.directoryPath += "/" + directoryName + "/";
        boolean isCreated = this.createDirectory(this.directoryPath);
        if (!isCreated) {
            throw new RuntimeException("Failed to create the directory: " + this.directoryPath);
        }
    }

    private boolean createDirectory(String directoryPath) {
      File dir = new File(directoryPath);
      boolean isDirCreated = dir.mkdir();
      return isDirCreated || dir.mkdirs();
    }

    @Override
    public void save(@NotNull String filename) {
        final File file = new File(this.directoryPath + filename);
        PrintWriter pw = null;
        try {
            file.createNewFile();
            pw = new PrintWriter(file);
            for (int i = 0; i <= generation; i++){
                pw.println(getSummary(i));
                pw.println();
            }

        } catch (IOException e) {
            System.err.println("Failed to save summary.");
        } finally {
            if (pw != null)
                pw.close();
        }
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
        return String.format("Generation: %d; Delta: %.4f, \n" +
            "Best >> %s <<\nWorst >> %s <<\nMedian >> %s <<\nMean: >> %.4f <<",
            generation, deltas.get(generation), elites.get(generation).toString(), worsts.get(generation).toString(),
            medians.get(generation).toString(), means.get(generation));
    }

    @Override
    public int getGeneration() {
        return this.generation;
    }

    public void generateCSVFile(String fileName) throws IOException {
        final File file = new File(this.directoryPath + fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.err.println("Failed to save csv file.");
        }

        CSVWriter writer = new CSVWriter(new FileWriter(this.directoryPath + fileName), '\t');
        String[] entries = "Best#Worst#Median#Mean".split("#");
        writer.writeNext(entries);
        for (int i=0; i<=generation; i++) {
            entries = (Double.toString(elites.get(i).getFitness()) + "#" +
                Double.toString(worsts.get(i).getFitness()) + "#" +
                Double.toString(medians.get(i).getFitness()) + "#" +
                Double.toString(means.get(i))).split("#");
            writer.writeNext(entries);
        }
        writer.close();
    }

    public void generatePlot(String chartTitle, String fileName) throws IOException {
        JFreeChart lineChartObject = ChartFactory.createLineChart(chartTitle,
          "Generation",
          "Fitness",
          this.createDataSet(),
          PlotOrientation.VERTICAL,
          true,true,false);

        int width = 1440;    /* Width of the image */
        int height = 1080;   /* Height of the image */
        File lineChartFile = new File( this.directoryPath + fileName);
        ChartUtilities.saveChartAsJPEG(lineChartFile ,lineChartObject, width ,height);
    }

    private DefaultCategoryDataset createDataSet() {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for (int i=0; i<=generation; i++) {
            dataSet.addValue(elites.get(i).getFitness(), "Best", Integer.toString(i));
            dataSet.addValue(worsts.get(i).getFitness(), "Worst", Integer.toString(i));
            dataSet.addValue(medians.get(i).getFitness(), "Median", Integer.toString(i));
            dataSet.addValue(means.get(i), "Mean", Integer.toString(i));
        }
        return dataSet;
    }

    public void copyMainFile(String fileName, String sourcePath) {
        String currentFilePath = System.getProperty("user.dir") + "/" + directoryPath + fileName;
        File source = new File(sourcePath);
        File destination = new File(currentFilePath);
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
