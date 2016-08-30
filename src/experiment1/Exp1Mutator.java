package experiment1;

import com.sun.istack.internal.NotNull;
import ga.components.Gene;
import ga.operations.Mutator;

/**
 * Created by david on 31/08/16.
 */
public class Exp1Mutator implements Mutator{

    private double prob;

    public Exp1Mutator(final double prob) {
        if (prob < 0 || prob > 1)
            throw new IllegalArgumentException("Value out of bound");
        this.prob = prob;
    }

    public double getProb() {
        return prob;
    }

    public void setProb(final double prob) {
        this.prob = prob;
    }

    @Override
    public void mutate(@NotNull Gene c) {
        if (prob > Math.random())
            c.setValue((Integer)c.getValue() ^ 1);
    }
}
