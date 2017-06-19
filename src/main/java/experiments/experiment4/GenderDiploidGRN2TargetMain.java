package experiments.experiment4;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhenyueqin on 19/6/17.
 */
public class GenderDiploidGRN2TargetMain {
    private static final int[] target1 = {-1, 1, -1, 1, -1, 1, -1, 1, -1, 1};
    private static final int[] target2 = {1, -1, 1, -1, 1, -1, 1, -1, 1, -1};
    private static final int maxCycle = 100;
    private static final int edgeSize = 20;
    private static final int perturbations = 300;
    private static final double geneMutationRate = 0.002;
    private static final double dominanceMutationRate = 0.001;
    private static final int numElites = 10;

    private static final int size = 1000;
    private static final int tournamentSize = 3;
    private static final double reproductionRate = 0.8;
    private static final int maxGen = 200;

    private static final double maxFit = 300;
    private static final double epsilon = .5;

    private static final int numberOfChildren = 2;

    private static final String summaryFileName = "Gender-Diploid-GRN-2-Target.sum";
    private static final String csvFileName = "Gender-Diploid-GRN-2-Target.csv";
    private static final String outputDirectory = "gender-diploid-grn-2-target";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private static Date date = new Date();

    private static final String plotTitle = "Gender GRN 2 Target Summary";
    private static final String plotFileName = "Gender-GRN-2-Target-Chart.png";

    public static void main(String[] args) throws IOException {

    }
}
