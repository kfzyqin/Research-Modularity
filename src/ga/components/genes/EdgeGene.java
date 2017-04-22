package ga.components.genes;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class EdgeGene extends Gene<Integer> {
    public static EdgeGene generateRandomEdgeGene() {
        return new EdgeGene(ThreadLocalRandom.current().nextInt(3) - 1);
    }

    public EdgeGene(@NotNull Integer value) {
        super(value);
        if (value != -1 && value != 0 && value != 1) {
            throw new IllegalArgumentException("Only -1, 0 and 1 are allowed.");
        }
    }

    public void setRandomValue() {
        this.value = ThreadLocalRandom.current().nextInt(3) - 1;
    }

    @Override
    public Gene<Integer> copy() {
        return new EdgeGene(this.value);
    }

    @Override
    public void setValue(@NotNull Integer value) {
        this.value = value;
    }

    public EdgeGene() {
        super(ThreadLocalRandom.current().nextInt(3) - 1);
    }

    @Override
    public String toString() {
        return "" + this.value;
    }
}
