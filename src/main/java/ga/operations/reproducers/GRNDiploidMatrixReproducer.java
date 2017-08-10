package ga.operations.reproducers;

import ga.components.chromosomes.SimpleDiploid;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.ExpressionMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhenyue Qin (秦震岳) on 4/7/17.
 * The Australian National University.
 */
public class GRNDiploidMatrixReproducer extends DiploidMatrixReproducer<SimpleDiploid> {

    public GRNDiploidMatrixReproducer(int matrixSideSize) {
        super(matrixSideSize);
    }

    public GRNDiploidMatrixReproducer(double matchProbability, int matrixSideSize) {
        super(matchProbability, matrixSideSize);
    }

    public GRNDiploidMatrixReproducer(double matchProbability, boolean toDoCrossover, int matrixSideSize) {
        super(matchProbability, toDoCrossover, matrixSideSize);
    }

    @Override
    protected List<SimpleDiploid> recombine(List<SimpleDiploid> mates) {
        List<SimpleDiploid> rtn = new ArrayList<>();

        SimpleDiploid parent1 = mates.get(0);
        SimpleDiploid parent2 = mates.get(1);

        List<SimpleMaterial> parent1Gametes = crossoverMatrix(mates.get(0));
        List<SimpleMaterial> parent2Gametes = crossoverMatrix(mates.get(1));

        SimpleMaterial dna1_1 = parent1Gametes.get(0).copy();
        SimpleMaterial dna1_2 = parent1Gametes.get(1).copy();
        SimpleMaterial dna2_1 = parent2Gametes.get(0).copy();
        SimpleMaterial dna2_2 = parent2Gametes.get(1).copy();

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
        rtn.add(new SimpleDiploid(dna2_1, dna1_2, mapping2.copy()));
        return rtn;
    }
}
