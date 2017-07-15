package ga.components.chromosomes;

/**
 * This interface requires a Chromosome to have a gender that guides
 * the recombination process in the reproduction.
 *
 * @author Siu Kei Muk (David) and Zhenyue Qin
 * @since 29/09/16.
 */
public interface Coupleable {
    boolean isMasculine();
}
