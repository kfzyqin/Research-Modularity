package experiment1;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.SimpleHaploid;
import ga.components.genes.Gene;
import ga.components.materials.SimpleDNA;
import ga.operations.recombinator.Recombinator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by david on 31/08/16.
 */
public class Exp1Recombinator implements Recombinator<SimpleHaploid> {

    public Exp1Recombinator() {
    }

    @Override
    public List<SimpleHaploid> recombine(@NotNull List<SimpleHaploid> mates) {
        List<SimpleHaploid> children = new ArrayList<>(2);

        SimpleHaploid child1 = mates.get(0).copy();
        SimpleHaploid child2 = mates.get(1).copy();

        SimpleDNA dna1 = child1.getMaterialsView().get(0);
        SimpleDNA dna2 = child2.getMaterialsView().get(0);

        final int length = child1.getLength();
        final int crossIndex = ThreadLocalRandom.current().nextInt(1,length-1);

        for (int i = crossIndex; i < length; i++) {
            final int i1 = ((Gene<Integer>) dna1.getGene(i)).getValue();
            final int i2 = ((Gene<Integer>) dna2.getGene(i)).getValue();
            dna1.getGene(i).setValue(i2);
            dna2.getGene(i).setValue(i1);
        }

        children.add(child1);
        children.add(child2);

        return children;
    }
}
