/**
 * Created by david on 27/08/16.
 */
public interface Fitness {
    double evaluate(DNAStrand phenotype);
    void signalChange();
}
