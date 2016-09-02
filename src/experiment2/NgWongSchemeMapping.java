package experiment2;

import com.sun.istack.internal.NotNull;
import ga.components.genes.BinaryGene;
import ga.components.genes.Gene;
import ga.components.materials.DNAStrand;
import ga.operations.dominanceMappings.DominanceMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2/09/16.
 */
public class NgWongSchemeMapping implements DominanceMapping<DNAStrand, DNAStrand>{

    private final double[][] matrix = new double[][] {{   0,  0,0.5,  1},
                                                      {   0,  0,  1,0.5},
                                                      { 0.5,  1,  1,  1},
                                                      {   0,0.5,  1,  1}};

    @Override
    public DominanceMapping<DNAStrand, DNAStrand> copy() {
        return new NgWongSchemeMapping();
    }

    @Override
    public DNAStrand map(@NotNull final List<DNAStrand> materials) {
        DNAStrand dna1 = materials.get(0);
        DNAStrand dna2 = materials.get(1);
        final int length = dna1.getSize();
        List<Gene> phenotype = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            NgWongSchemeGenes gene1 = (NgWongSchemeGenes) dna1.getGene(i);
            NgWongSchemeGenes gene2 = (NgWongSchemeGenes) dna2.getGene(i);
            promote(gene1,gene2);
            final int row = toIndex(gene1.getValue());
            final int col = toIndex(gene2.getValue());
            phenotype.add(new BinaryGene(getGeneValue(row,col)));
        }
        return new DNAStrand(phenotype);
    }

    private int getGeneValue(final int row, final int column) {
        return (Math.random() < matrix[row][column]) ? 1 : 0;
    }

    private void promote(NgWongSchemeGenes gene1, NgWongSchemeGenes gene2) {
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
}
