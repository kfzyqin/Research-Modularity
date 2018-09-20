package ga.collections;

import au.com.bytecode.opencsv.CSVWriter;
import ga.components.chromosomes.Chromosome;
import ga.components.genes.DataGene;
import ga.components.materials.GRN;
import ga.components.materials.Material;
import ga.components.materials.SimpleMaterial;
import ga.others.GeneralMethods;
import org.apache.commons.io.FileUtils;
import org.graphstream.graph.Graph;
import org.jetbrains.annotations.NotNull;
import org.jfree.data.category.DefaultCategoryDataset;
import tools.GRNModularity;
import tools.ListTools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a simple implementation of statistics bookkeeping that
 * keeps records of the best, worst and median individual, also the mean fitness of each generation.
 *
 * Created by Zhenyue Qin on 10/06/2017.
 * The Australian National University.
 */
public class DetailedStatistics <C extends Chromosome> extends BaseStatistics<C> {

    List<Individual<C>> elites; // best individuals
    List<Individual<C>> worsts;  // worst individuals
    List<Individual<C>> medians;  // median individuals
    List<Double> means; // mean fitness values
    List<Double> deltas;
    List<Double> averageEdgeNumbers;
    List<Double> edgeNumberStdDevs;

    List<Individual<C>> fittestGRNs;
    List<Individual<C>> mostModularGRNs;
    List<Double> meanMods;

    List<List<DataGene[][]>> allGenerationPerturbations;

    List<List<String>> populationPhenotypeList;

    public DetailedStatistics() {
        generation = 0;
        elites = new ArrayList<>();
        worsts = new ArrayList<>();
        medians = new ArrayList<>();
        means = new ArrayList<>();
        deltas = new ArrayList<>();

        /*
        Record edge density information
         */
        averageEdgeNumbers = new ArrayList<>();
        edgeNumberStdDevs = new ArrayList<>();
        allGenerationPerturbations = new ArrayList<>();

        fittestGRNs = new ArrayList<>();
        mostModularGRNs = new ArrayList<>();
        meanMods = new ArrayList<>();

        populationPhenotypeList = new ArrayList<>();
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

        /*
        Record edge density information
         */
        averageEdgeNumbers = new ArrayList<>(maxGen);
        edgeNumberStdDevs = new ArrayList<>(maxGen);
        allGenerationPerturbations = new ArrayList<>(maxGen);

        fittestGRNs = new ArrayList<>(maxGen);
        mostModularGRNs = new ArrayList<>(maxGen);
        meanMods = new ArrayList<>(maxGen);

        populationPhenotypeList = new ArrayList<>(maxGen);
    }

    private DetailedStatistics(@NotNull final List<Individual<C>> elites,
                               @NotNull final List<Individual<C>> worsts,
                               @NotNull final List<Individual<C>> medians,
                               @NotNull final List<Double> means,
                               @NotNull final List<Double> deltas,
                               @NotNull final List<Double> averageEdgeNumber,
                               @NotNull final List<Double> edgeNumberStdDev,
                               @NotNull final List<Individual<C>> fittestGRNs,
                               @NotNull final List<Individual<C>> mostModularGRNs,
                               @NotNull final List<Double> meanMods,
                               @NotNull final List<List<String>> populationPhenotypeList) {
        generation = elites.size();
        this.elites = new ArrayList<>(generation);
        this.means = new ArrayList<>(means);
        this.deltas = new ArrayList<>(deltas);
        for (int i = 0; i < generation; i++) {
            this.elites.add(elites.get(i).copy());
            this.worsts.add(worsts.get(i).copy());
            this.medians.add(medians.get(i).copy());
            this.means.add(means.get(i));
            this.averageEdgeNumbers.add(averageEdgeNumber.get(i));
            this.edgeNumberStdDevs.add(edgeNumberStdDev.get(i));

            this.fittestGRNs.add(fittestGRNs.get(i));
            this.mostModularGRNs.add(mostModularGRNs.get(i));
            this.meanMods.add(meanMods.get(i));
            this.populationPhenotypeList.add(populationPhenotypeList.get(i));
        }
    }

    @Override
    public DetailedStatistics<C> copy() {
        return new DetailedStatistics<>(elites, worsts, medians, means, deltas, averageEdgeNumbers, edgeNumberStdDevs,
                fittestGRNs, mostModularGRNs, meanMods, populationPhenotypeList);
    }

    private double getAverageFitnessValueOfAPopulation(@NotNull final List<Individual<C>> data) {
        double fitnessValueSum = 0;
        for (Individual<C> individual : data) {
            fitnessValueSum += individual.getFitness();
        }
        return fitnessValueSum / data.size();
    }

    private List<Graph> getAllGRNs(@NotNull final List<Individual<C>> data) {
        List<Graph> grns = new ArrayList<>();
        for (Individual<C> individual : data) {
            Chromosome c = individual.getChromosome();
            Material phenotype = c.getPhenotype(false);
            List<Integer> intGRNList = ((GRN) phenotype).getIntGRNList();
            grns.add(GRNModularity.getGRNGraph(intGRNList));
        }
        return grns;
    }

    private List<Double> getAllModularities (@NotNull final List<Individual<C>> data) {
        List<Double> modularities = new ArrayList<>();
        List<Graph> grns = this.getAllGRNs(data);
        for (Graph aGRN : grns) {
            modularities.add(GRNModularity.getGRNModularity(aGRN));
        }
        return modularities;
    }

    private Individual<C> getFittestMostModularIndividual(List<Individual<C>> data) {
        Individual<C> rtn = data.get(0);
        for (Individual<C> aData : data) {
            if (aData.getFitness() >= data.get(0).getFitness()) {
                if (aData.getGRNModularity() > rtn.getGRNModularity()) {
                    rtn = aData.copy();
                }
            } else {
                return rtn;
            }
        }
        return rtn;
    }

    private Individual<C> getMostModularFittestIndividual(List<Individual<C>> data) {
        List<Double> mods = this.getAllModularities(data);
        int modArgMax = ListTools.getArgMax(mods);
        Individual<C> rtn = data.get(modArgMax);

        for (Individual<C> aData : data) {
            if (aData.getGRNModularity() >= rtn.getGRNModularity()) {
                if (aData.getFitness() > rtn.getFitness()) {
                    rtn = aData.copy();
                }
            }
        }
        return rtn;
    }

    protected List<String> getPopulationPhenotype(List<Individual<C>> data) {
        List<String> populationPhenotype = new ArrayList<>();
        for (Individual i : data) {
            populationPhenotype.add(i.chromosome.getPhenotype(false).toString());
        }
        return populationPhenotype;
    }

    @Override
    public void record(List<Individual<C>> data) {
        Individual<C> elite = this.getFittestMostModularIndividual(data);
        Individual<C> worst = data.get(data.size() - 1).copy();
        Individual<C> median = data.get(data.size() / 2).copy();

        double averageFitnessValue = this.getAverageFitnessValueOfAPopulation(data);

        int[] currentGenerationEdgeNumbers = new int[data.size()];
        for (int i=0; i<data.size(); i++) {
            currentGenerationEdgeNumbers[i] = GeneralMethods.getEdgeNumber(
                    (SimpleMaterial) data.get(i).copy().getChromosome().getPhenotype(false));
        }
        double averageEdgeNumber = GeneralMethods.getAverageNumber(currentGenerationEdgeNumbers);
        double stdDevEdgeNumber = GeneralMethods.getStandardDeviation(currentGenerationEdgeNumbers);

        elites.add(elite);
        worsts.add(worst);
        medians.add(median);
        means.add(averageFitnessValue);

        averageEdgeNumbers.add(averageEdgeNumber);
        edgeNumberStdDevs.add(stdDevEdgeNumber);

        if (generation == 0)
            deltas.add(elite.getFitness());
        else
            deltas.add(elite.getFitness() - elites.get(generation-1).getFitness());

        List<Double> mods = this.getAllModularities(data);
        mostModularGRNs.add(this.getMostModularFittestIndividual(data));

        meanMods.add(ListTools.getListAvg(mods));

        populationPhenotypeList.add(this.getPopulationPhenotype(data));

//        this.allGenerationPerturbations.add(new ArrayList(elite.getIndividualSPerturbations()));
//        for (DataGene[][] aDataGene : this.allGenerationPerturbations.get(allGenerationPerturbations.size()-1)) {
//            System.out.println(Arrays.toString(aDataGene[0]));
//        }
    }

    public void setDirectory(@NotNull String directoryName) {
        this.directoryPath += "/" + directoryName + "/";
        boolean isCreated = this.createDirectory(this.directoryPath);
        boolean isCreatedSub = this.createDirectory(this.directoryPath + "/population-phenotypes/");
        if (!isCreated || !isCreatedSub) {
            throw new RuntimeException("Failed to create the directory: " + this.directoryPath);
        }
    }

    private boolean createDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        boolean isDirCreated = dir.mkdir();
        return isDirCreated || dir.mkdirs();
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
        return String.format("Generation: %d; Delta: %.4f, \n" + "Best >> %s <<\n",
                generation, deltas.get(generation), elites.get(generation).toString());
    }

    @Override
    public int getGeneration() {
        return this.generation;
    }

    public void generatePopulationPhenotypesOfAllGenerations(String fileName) throws IOException {

        for (int i=0; i<=generation; i++) {
            BufferedWriter pheOutputWriter;
            String aFileName = this.directoryPath + fileName + "_gen_" + ((Integer) i).toString() + ".lists";
            pheOutputWriter = new BufferedWriter(new FileWriter(aFileName));
            List<String> aPopulationPhenotype = populationPhenotypeList.get(i);
            for (String aIndPhenotype : aPopulationPhenotype) {
                pheOutputWriter.write(aIndPhenotype);
                pheOutputWriter.newLine();
            }
            pheOutputWriter.close();
        }
    }

    public void generateFitnessModularityGRNs(String fileName) throws IOException {
        String modName = this.directoryPath + fileName + "_mod.list";
        String fitName = this.directoryPath + fileName + "_fit.list";

        BufferedWriter modOutputWriter;
        modOutputWriter = new BufferedWriter(new FileWriter(modName));

        BufferedWriter fitOutputWriter;
        fitOutputWriter = new BufferedWriter(new FileWriter(fitName));

        for (int i=0; i<=generation; i++) {
            modOutputWriter.write((mostModularGRNs.get(i).getChromosome().getPhenotype(false)).toString());
            fitOutputWriter.write((elites.get(i).getChromosome().getPhenotype(false)).toString());
            modOutputWriter.newLine();
            fitOutputWriter.newLine();
        }

        modOutputWriter.flush();
        modOutputWriter.close();
        fitOutputWriter.flush();
        fitOutputWriter.close();
    }

    public void generateNormalCSVFile(String fileName) throws IOException {
        final File file = new File(this.directoryPath + fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.err.println("Failed to save csv file.");
        }

        CSVWriter writer = new CSVWriter(new FileWriter(this.directoryPath + fileName), '\t');
        String[] entries = ("Best#Worst#Median#Mean#AvgEdgeNumber#StdDevEdgeNumber#FittestModularity#MostModularity" +
                "#MostModFitness").split("#");
        writer.writeNext(entries);
        for (int i=0; i<=generation; i++) {
            entries = (
                    Double.toString(elites.get(i).getFitness()) + "#" +
                            Double.toString(worsts.get(i).getFitness()) + "#" +
                            Double.toString(medians.get(i).getFitness()) + "#" +
                            Double.toString(means.get(i)) + "#" +
                            Double.toString(averageEdgeNumbers.get(i)) + "#" +
                            Double.toString(edgeNumberStdDevs.get(i)) + "#" +
                            Double.toString(((GRN) elites.get(i).getChromosome().getPhenotype(false)).getGRNModularity()) + "#" +
                            Double.toString(((GRN) mostModularGRNs.get(i).getChromosome().getPhenotype(false)).getGRNModularity()) + "#" +
                            Double.toString(mostModularGRNs.get(i).getFitness())
            ).split("#");
            writer.writeNext(entries);
        }
        writer.close();
    }

    @Override
    protected DefaultCategoryDataset createDataSet() {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for (int i=0; i<=generation; i++) {
            dataSet.addValue(elites.get(i).getFitness(), "Best", Integer.toString(i));
//            dataSet.addValue(worsts.get(i).getFitness(), "Worst", Integer.toString(i));
            dataSet.addValue(medians.get(i).getFitness(), "Median", Integer.toString(i));
            dataSet.addValue(means.get(i), "Mean", Integer.toString(i));
            dataSet.addValue(((GRN) elites.get(i).getChromosome().getPhenotype(false)).getGRNModularity(), "Most Modularity", Integer.toString(i));
            dataSet.addValue(meanMods.get(i), "Mean Modularity", Integer.toString(i));
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

    private String getChromosomePhenotype(int generation) {
        return this.elites.get(generation).getChromosome().getPhenotype(false).toString();
    }

    public void storePerturbations(@NotNull final String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(this.directoryPath + fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
//        for (int i=0; i<this.allGenerationPerturbations.size(); i++) {
//            for (DataGene[][] aDataGene : this.allGenerationPerturbations.get(i)) {
//                System.out.println(Arrays.toString(aDataGene[0]));
//            }
//        }
//        System.out.println(Arrays.toString(this.allGenerationPerturbations.get(0).get(0)[0]));
        oos.writeObject(this.allGenerationPerturbations);
        oos.close();
    }

    public List<List<DataGene[][]>> getAllGenerationPerturbations() {
        return this.allGenerationPerturbations;
    }

}
