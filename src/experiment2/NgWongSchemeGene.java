package experiment2;

import com.sun.istack.internal.NotNull;
import ga.components.genes.Gene;

/**
 * Created by david on 2/09/16.
 */
public class NgWongSchemeGene implements Gene<Character>{

    private char value;

    public NgWongSchemeGene(char value) {
        validityFilter(value);
        this.value = value;
    }

    private void validityFilter(char value) {
        switch (value) {
            case 'i':
            case '1':
            case '0':
            case 'o':
                return;
            default:
                throw new IllegalArgumentException("Invalid value given. The value must be one of 'i', 'I', 'O' or 'o'.");
        }

    }

    @Override
    public Gene<Character> copy() {
        return new NgWongSchemeGene(value);
    }

    @Override
    public Character getValue() {
        return value;
    }

    @Override
    public void setValue(@NotNull Character value) {
        validityFilter(value);
        this.value = value;
    }
}
