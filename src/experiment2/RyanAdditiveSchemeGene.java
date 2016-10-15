package experiment2;

import com.sun.istack.internal.NotNull;
import ga.components.genes.Gene;

/**
 * Created by david on 3/09/16.
 */
public class RyanAdditiveSchemeGene extends Gene<Character> {

    private static final char[] values = {'A','B','C','D'};
    private char value;

    public RyanAdditiveSchemeGene(final char value) {
        super(value);
        filter(value);
        // this.value = value;
    }

    private void filter(final char value) {
        if (value < 'A' || value > 'D')
            throw new IllegalArgumentException("Invalid value. The permitted values are 'A', 'B', 'C' or 'D'.");
    }

    @Override
    public Gene<Character> copy() {
        return new RyanAdditiveSchemeGene(value);
    }

    @Override
    public void setValue(@NotNull Character value) {
        filter(value);
        this.value = value;
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }
}
