package ga.operations.dominanceMapMutators;

import ga.components.genes.EdgeGene;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

/**
 * Created by zhenyueqin on 17/6/17.
 */
public class DiploidDominanceMapMutator implements ExpressionMapMutator {

    private double evolvingMutationRate;

    public DiploidDominanceMapMutator(final double evolvingMutationRate) {
        setEvolvingMutationRate(evolvingMutationRate);
    }

    public void setEvolvingMutationRate(double probability) {
        if (probability > 1 || probability < 0) throw new IllegalArgumentException("Invalid probability value.");
        this.evolvingMutationRate = probability;
    }

    public double getEvolvingMutationRate() {
        return this.evolvingMutationRate;
    }

    @Override
    public void mutate(ExpressionMap expressionMap) {
        int expressionMapSize = ((DiploidEvolvedMap) expressionMap).getSize();
        for (int i = 0; i<expressionMapSize; i++) {
            if (Math.random() < this.evolvingMutationRate) {
                ((EdgeGene) ((DiploidEvolvedMap) expressionMap).getDominanceMap().getGene(i)).mutate();
            }
        }
    }

}
