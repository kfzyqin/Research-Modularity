package ga.operations;

import ga.components.Gene;
import ga.others.Copyable;

import java.util.List;

/**
 * Created by david on 27/08/16.
 */
public interface DominanceMapping extends Copyable<DominanceMapping> {
    Gene map(List<Gene> genes);
}
