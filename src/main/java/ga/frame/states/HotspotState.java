package ga.frame.states;

import ga.collections.Population;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.WithHotspot;
import ga.components.hotspots.Hotspot;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.hotspotMutators.HotspotMutator;
import ga.operations.mutators.Mutator;
import ga.operations.reproducers.Reproducer;
import ga.operations.selectionOperators.selectors.Selector;

/**
 * Created by Zhenyue Qin (秦震岳) on 15/7/17.
 * The Australian National University.
 */
public interface HotspotState<C extends Chromosome & WithHotspot> {
    public void mutateHotspot();
}
