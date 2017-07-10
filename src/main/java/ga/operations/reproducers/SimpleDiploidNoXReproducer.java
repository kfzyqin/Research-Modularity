package ga.operations.reproducers;

import ga.components.chromosomes.SimpleDiploid;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhenyue Qin (秦震岳) on 9/7/17.
 * The Australian National University.
 */
public class SimpleDiploidNoXReproducer extends DiploidMatrixReproducer<SimpleDiploid> {
    public SimpleDiploidNoXReproducer(int matrixSideSize) {
        super(matrixSideSize);
    }

    public SimpleDiploidNoXReproducer(double matchProbability, int matrixSideSize) {
        super(matchProbability, matrixSideSize);
    }

    public SimpleDiploidNoXReproducer(double matchProbability, boolean toDoCrossover, int matrixSideSize) {
        super(matchProbability, toDoCrossover, matrixSideSize);
    }

    @Override
    protected List<SimpleDiploid> recombine(List<SimpleDiploid> mates) {
        List<SimpleDiploid> rtn = new ArrayList<>();

        SimpleDiploid parent1 = mates.get(0);
        SimpleDiploid parent2 = mates.get(1);

        SimpleMaterial dna1_1 = parent1.getMaterialsView().get(0).copy();
        SimpleMaterial dna1_2 = parent1.getMaterialsView().get(1).copy();
        SimpleMaterial dna2_1 = parent2.getMaterialsView().get(0).copy();
        SimpleMaterial dna2_2 = parent2.getMaterialsView().get(1).copy();

        ExpressionMap mapping1 = parent1.getMapping().copy();
        ExpressionMap mapping2 = parent2.getMapping().copy();

//        if (Math.random() < matchProbability) {
//            SimpleMaterial tmp = dna1_2;
//            dna1_2 = dna2_2;
//            dna2_2 = tmp;
//        }
//
//        if (Math.random() < matchProbability) {
//            ExpressionMap tmp = mapping1;
//            mapping1 = mapping2;
//            mapping2 = tmp;
//        }

        rtn.add(new SimpleDiploid(dna1_1, dna2_2, mapping1.copy()));
//        rtn.add(new SimpleDiploid(dna2_1, dna2_2, mapping2.copy()));
        return rtn;
    }
}
