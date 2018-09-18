package ga.operations.fitnessFunctions;

import ga.components.genes.DataGene;

import java.util.ArrayList;
import java.util.List;

public class GRNFitnessFunctionMultipleTargetsBalanceAsymmetricResample extends GRNFitnessFunctionMultipleTargetsBalanceAsymmetric {

    protected final int perturbationPathUpBound;

    public GRNFitnessFunctionMultipleTargetsBalanceAsymmetricResample(int[][] targets, int maxCycle, int perturbations,
                                                                      double perturbationRate, double stride, int perturbationPathUpBound) {
        super(targets, maxCycle, perturbations, perturbationRate, stride);
        this.perturbationPathUpBound = perturbationPathUpBound;
    }

    public GRNFitnessFunctionMultipleTargetsBalanceAsymmetricResample(int[][] targets, int maxCycle,
                                                                      double perturbationRate, double stride, int perturbationPathUpBound) {
        super(targets, maxCycle, perturbationRate, stride);
        this.perturbationPathUpBound = perturbationPathUpBound;
    }

    public GRNFitnessFunctionMultipleTargetsBalanceAsymmetricResample(int[][] targets, int maxCycle, int perturbations,
                                                                      double perturbationRate,
                                                                      List<Integer> thresholdOfAddingTarget,
                                                                      double stride, int perturbationPathUpBound) {
        super(targets, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget, stride);
        this.perturbationPathUpBound = perturbationPathUpBound;
    }

    public GRNFitnessFunctionMultipleTargetsBalanceAsymmetricResample(int[][] targets, int maxCycle, int perturbations,
                                                                      double perturbationRate,
                                                                      List<Integer> thresholdOfAddingTarget,
                                                                      String outputPath, double stride, int perturbationPathUpBound) {
        super(targets, maxCycle, perturbations, perturbationRate, thresholdOfAddingTarget, outputPath, stride);
        this.perturbationPathUpBound = perturbationPathUpBound;
    }

    @Override
    protected DataGene[][] generateInitialAttractors(int setSize, double probability, int[] target) {
        DataGene[][] returnables = new DataGene[setSize][target.length];

        for (int i=0; i<setSize; i++) {
            int mutatedGeneNo;
            List<Integer> js;
            do {
                js = new ArrayList<>();
                mutatedGeneNo = 0;
                for (int j = 0; j < target.length; j++) {
                    returnables[i][j] = new DataGene(target[j]);
                    if (Math.random() < probability) {
//                    returnables[i][j].flip();
                        js.add(j);
                        mutatedGeneNo += 1;
                    }
                }
            } while (mutatedGeneNo > this.perturbationPathUpBound) ;
            for (int anJ : js) {
                returnables[i][anJ].flip();
            }

        }
        return returnables;
    }
}
