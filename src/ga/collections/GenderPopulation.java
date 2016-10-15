package ga.collections;

import com.sun.istack.internal.NotNull;
import ga.components.chromosome.Chromosome;
import ga.components.chromosome.Coupleable;

/**
 * Created by david on 29/09/16.
 */
public class GenderPopulation<G extends Chromosome & Coupleable> extends Population<G> {

    protected int numOfFemale;
    protected int numOfMale;
    protected double maleProportion = 0.5;
    protected int maxNumOfMale;
    protected int maxNumOfFemale;

    public GenderPopulation(final int size) {
        super(size);
        numOfFemale = 0;
        numOfMale = 0;
        maxNumOfMale = (int) Math.round(size*maleProportion);
        maxNumOfFemale = size - maxNumOfMale;
    }

    public GenderPopulation(final int size, final double maleProportion) {
        super(size);
        setMaleProportion(maleProportion);
    }

    private void filter(final double maleProportion) {
        if (maleProportion <= 0 || maleProportion >= 1) throw new IllegalArgumentException("Invalid proportion value.");
    }

    @Override
    public void addCandidate(@NotNull final Individual<G> candidate) {
        final boolean masculine = candidate.getChromosome().isMasculine();
        if (masculine && numOfMale < maxNumOfMale) {
            numOfMale++;
            super.addCandidate(candidate);
        } else if (!masculine && numOfFemale < maxNumOfFemale){
            numOfFemale++;
            super.addCandidate(candidate);
        }
    }

    @Override
    public boolean nextGeneration() {
        if (!isReady()) return false;
        numOfFemale = 0;
        numOfMale = 0;
        return super.nextGeneration();
    }

    public double getMaleProportion() {
        return maleProportion;
    }

    public void setMaleProportion(final double maleProportion) {
        filter(maleProportion);
        this.maleProportion = maleProportion;
        maxNumOfMale = (int) Math.round(size*maleProportion);
        maxNumOfFemale = size - maxNumOfMale;
    }
}