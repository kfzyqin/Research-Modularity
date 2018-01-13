package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.chromosomes.Chromosome;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.*;

/**
 * Created by Zhenyue Qin (秦震岳) on 4/7/17.
 * The Australian National University.
 */
public abstract class BaseStatistics <C extends Chromosome> implements Statistics<C> {
    protected String directoryPath = "generated-outputs";
    protected int generation;

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

    protected abstract DefaultCategoryDataset createDataSet();

    public void save(@NotNull final String filename) {
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
            System.err.println("Failed to save file.");
        } finally {
            if (pw != null)
                pw.close();
        }
    }
}
