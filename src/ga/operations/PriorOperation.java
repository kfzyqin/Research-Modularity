package ga.operations;

import ga.collections.Population;
import ga.components.Chromosome;

import java.util.List;

/**
 * Created by david on 28/08/16.
 */
public interface PriorOperation<T extends Chromosome> {
    List<T> preOperate(Population<T> population);
}
