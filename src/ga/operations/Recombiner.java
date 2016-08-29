package ga.operations;

import ga.components.Chromosome;

import java.util.List;

/**
 * Created by david on 26/08/16.
 */
public interface Recombiner<T extends Chromosome> {
    List<T> recombine(List<T> mates);
}
