package ga.operations.expressionMaps;

import com.sun.istack.internal.NotNull;
import ga.components.genes.DataGene;
import ga.components.genes.EdgeGene;
import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Zhenyue Qin on 13/06/2017.
 * The Australian National University.
 */
public class DiploidEvolvedMap implements ExpressionMap<SimpleMaterial, SimpleMaterial> {

    private SimpleMaterial dominanceMap;

    public DiploidEvolvedMap(final SimpleMaterial dominanceValue) {
        this.dominanceMap = dominanceValue.copy();
    }

    public DiploidEvolvedMap(final int mapSize) {
        List<Gene> genes = new ArrayList<>(mapSize);
        for (int i=0; i<mapSize; i++) {
            genes.add(new EdgeGene());
        }
        this.dominanceMap = new SimpleMaterial(genes);
    }

    @Override
    public ExpressionMap<SimpleMaterial, SimpleMaterial> copy() {
        return new DiploidEvolvedMap(this.dominanceMap.copy());
    }

    public int getSize() {
        return this.dominanceMap.getSize();
    }

    public SimpleMaterial getDominanceMap() {
        return this.dominanceMap;
    }

    @Override
    public SimpleMaterial map(List<SimpleMaterial> materials) {
        SimpleMaterial dna1 = materials.get(0);
        SimpleMaterial dna2 = materials.get(1);

        List<Gene> genes = new ArrayList<>(dna1.getSize());
        for (int i = 0; i < dna1.getSize(); i++) {
//            int dominanceValue = (int) this.dominanceMap.getGene(i).getValue();
//            int dna1Value = (int) dna1.getGene(i).getValue();
//            int dna2Value = (int) dna2.getGene(i).getValue();

//            if (dominanceValue == dna1Value || dominanceValue == dna2Value) {
//                genes.add(new EdgeGene(dominanceValue));
//            } else {
//                genes.add(new EdgeGene(dna1Value));
//            }

            int dna1Value = (int) dna1.getGene(i).getValue();
            int dna2Value = (int) dna2.getGene(i).getValue();


            if (dna1Value == 0 && dna2Value == 0) {
                genes.add(new EdgeGene(0));
            } else {
                if (dna1Value == 1 || dna2Value == 1) {
                    genes.add(new EdgeGene(1));
                } else {
                    genes.add(new EdgeGene(-1));
                }
            }
        }
        return new SimpleMaterial(genes);
    }

    @Override
//    public String toString() {
//        return "Evolved Diploid Dominance Map: " + this.dominanceMap;
//    }
    public String toString() {
        return "Just use the first parent DNA type";
    }
}
