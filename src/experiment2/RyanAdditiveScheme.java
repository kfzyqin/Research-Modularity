package experiment2;

import com.sun.istack.internal.NotNull;
import ga.components.genes.BinaryGene;
import ga.components.genes.Gene;
import ga.components.materials.DNAStrand;
import ga.operations.dominanceMappings.DominanceMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 3/09/16.
 */
public class RyanAdditiveScheme implements DominanceMapping<DNAStrand, DNAStrand> {

    public RyanAdditiveScheme() {
    }

    private int toPoints(final char value) {
        switch (value) {
            case 'A':
                return 2;
            case 'B':
                return 3;
            case 'C':
                return 7;
            case 'D':
                return 9;
            default:
                throw new IllegalArgumentException("Invalid value. The permitted values are 'A', 'B', 'C' or 'D'.");
        }
    }

    @Override
    public DominanceMapping<DNAStrand, DNAStrand> copy() {
        return new RyanAdditiveScheme();
    }

    @Override
    public DNAStrand map(@NotNull List<DNAStrand> materials) {
        DNAStrand dna1 = materials.get(0);
        DNAStrand dna2 = materials.get(1);
        final int length = dna1.getSize();
        List<Gene<Integer>> genes = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            final int value1 = toPoints((char)dna1.getGene(i).getValue());
            final int value2 = toPoints((char)dna2.getGene(i).getValue());
            genes.add((value1 + value2 > 10) ? new BinaryGene(1) : new BinaryGene(0));
        }
        return new DNAStrand(genes);
    }
}
