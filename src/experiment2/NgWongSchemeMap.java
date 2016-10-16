package experiment2;

import com.sun.istack.internal.NotNull;
import ga.components.genes.BinaryGene;
import ga.components.genes.Gene;
import ga.components.materials.SimpleDNA;
import ga.operations.dominanceMap.DominanceMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2/09/16.
 */
public class NgWongSchemeMap implements DominanceMap<SimpleDNA, SimpleDNA> {

    private final double[][] matrix = new double[][] {{   0,  0,0.5,  1},
                                                      {   0,  0,  1,0.5},
                                                      { 0.5,  1,  1,  1},
                                                      {   0,0.5,  1,  1}};

    @Override
    public DominanceMap<SimpleDNA, SimpleDNA> copy() {
        return new NgWongSchemeMap();
    }

    @Override
    public SimpleDNA map(@NotNull final List<SimpleDNA> materials) {
        SimpleDNA dna1 = materials.get(0);
        SimpleDNA dna2 = materials.get(1);
        final int length = dna1.getSize();
        List<Gene> phenotype = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            NgWongSchemeGene gene1 = (NgWongSchemeGene) dna1.getGene(i);
            NgWongSchemeGene gene2 = (NgWongSchemeGene) dna2.getGene(i);
            promote(gene1,gene2);
            final int row = toIndex(gene1.getValue());
            final int col = toIndex(gene2.getValue());
            phenotype.add(new BinaryGene(getGeneValue(row,col)));
        }
        return new SimpleDNA(phenotype);
    }

    private int getGeneValue(final int row, final int column) {
        return (Math.random() < matrix[row][column]) ? 1 : 0;
    }

    private void promote(NgWongSchemeGene gene1, NgWongSchemeGene gene2) {
        char value1 = gene1.getValue();
        char value2 = gene2.getValue();
        switch (value1) {
            case '1':
                if (value2 == 'i')
                    gene2.setValue('1');
                return;
            case 'i':
                if (value2 == '1')
                    gene1.setValue('1');
                return;
            case '0':
                if (value2 == 'o')
                    gene2.setValue('0');
                return;
            case 'o':
                if (value2 == '0')
                    gene1.setValue('0');
        }
    }

    private int toIndex(char c) {
        switch (c) {
            case '0':
                return 0;
            case 'o':
                return 1;
            case '1':
                return 2;
            case 'i':
                return 3;
            default:
                return -1;
        }
    }

    @Override
    public String toString() {
        return String.format(" |  0|  o|  1|  i\n-----------------\n0|%.1f|%.1f|%.1f|%.1f\n" +
                "o|%.1f|%.1f|%.1f|%.1f\n1|%.1f|%.1f|%.1f|%.1f\ni|%.1f|%.1f|%.1f|%.1f\n",
                matrix[0][0], matrix[0][1], matrix[0][2], matrix[0][3],
                matrix[1][0], matrix[1][1], matrix[1][2], matrix[1][3],
                matrix[2][0], matrix[2][1], matrix[2][2], matrix[2][3],
                matrix[3][0], matrix[3][1], matrix[3][2], matrix[3][3]);
    }
}
