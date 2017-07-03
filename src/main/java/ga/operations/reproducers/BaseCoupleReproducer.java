package ga.operations.reproducers;

import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.Coupleable;

/**
 * Created by Zhenyue Qin (秦震岳) on 3/7/17.
 * The Australian National University.
 */
public abstract class BaseCoupleReproducer<G extends Chromosome & Coupleable>
        extends BaseReproducer<G> implements CoupleReproducer<G> {

}
