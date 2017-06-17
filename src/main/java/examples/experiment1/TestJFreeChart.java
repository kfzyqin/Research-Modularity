package examples.experiment1;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * Created by Zhenyue Qin on 13/06/2017.
 * The Australian National University.
 */
public class TestJFreeChart extends ApplicationFrame {
  public TestJFreeChart(String applicationTitle , String chartTitle ) {
    super(applicationTitle);
    JFreeChart lineChartObject = ChartFactory.createLineChart(
      chartTitle,
      "Years","Number of Schools",
      createDataset(),
      PlotOrientation.VERTICAL,
      true,true,false);

    ChartPanel chartPanel = new ChartPanel(lineChartObject);
    chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
    setContentPane( chartPanel );
  }

  private DefaultCategoryDataset createDataset( ) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
    dataset.addValue( 15 , "schools" , "1970" );
    dataset.addValue( 30 , "schools" , "1980" );
    dataset.addValue( 60 , "schools" ,  "1990" );
    dataset.addValue( 120 , "chins" , "2000" );
    dataset.addValue( 240 , "chins" , "2010" );
    dataset.addValue( 300 , "chins" , "2014" );
    return dataset;
  }

  public static void main( String[ ] args ) {
    TestJFreeChart chart = new TestJFreeChart(
      "School Vs Years" ,
      "Numer of Schools vs years");

    chart.pack();
    RefineryUtilities.centerFrameOnScreen(chart);
    chart.setVisible(true);
  }
}
