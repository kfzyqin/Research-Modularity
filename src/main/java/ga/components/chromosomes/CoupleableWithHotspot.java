package ga.components.chromosomes;

import ga.components.hotspots.Hotspot;

/**
 * This interface requires a Chromosome to have a gender and contain a Hotspot object that guides
 * the recombination process in the reproduction.
 *
 * Created by Zhenyue Qin (秦震岳) on 17/6/17.
 * The Australian National University.
 */
public interface CoupleableWithHotspot extends Coupleable, WithHotspot {

}
