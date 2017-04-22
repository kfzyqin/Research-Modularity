package ga.components.genes;

import com.sun.istack.internal.NotNull;

/**
 * Created by Zhenyue Qin on 6/04/2017.
 * The Australian National University.
 */
public class DataGene extends Gene<Integer> {
    public static DataGene generateRandomDataGene() {
    return new DataGene((Math.random() < 0.5) ? -1 : 1);
  }

    public DataGene(@NotNull final int value) {
        super(value);
        if (value != -1 && value != 1) {
            throw new IllegalArgumentException("Only -1 and 1 are allowed.");
        }
    }

    public DataGene() {
    super((Math.random() < 0.5) ? -1 : 1);
  }

    public void flip() {
        if (this.value == -1) {
            this.value = 1;
        } else if (this.value == 1) {
            this.value = -1;
        }
    }

    public void setRandomValue() {
        if (Math.random() < 0.5) {
            this.value = -1;
        } else {
            this.value = 1;
        }
    }

    @Override
    public Gene<Integer> copy() {
    return new DataGene(this.value);
  }

    @Override
    public void setValue(@NotNull Integer value) {
        if (value != -1 && value != 1) {
            throw new IllegalArgumentException("Only binary values are allowed.");
        }
        this.value = value;
    }

    @Override
    public String toString() {
    return "" + this.value;
  }
}

