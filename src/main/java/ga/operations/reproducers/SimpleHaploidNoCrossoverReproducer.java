package ga.operations.reproducers;

import ga.components.chromosomes.SimpleHaploid;
import ga.components.materials.SimpleMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Zhenyue Qin (秦震岳) on 8/7/17.
 * The Australian National University.
 */
public class SimpleHaploidNoCrossoverReproducer extends HaploidReproducer<SimpleHaploid>  {
    @Override
    public List<SimpleHaploid> reproduce(List<SimpleHaploid> mates) {
        List<SimpleHaploid> children = new ArrayList<>(2);

        SimpleHaploid child1 = mates.get(0).copy();
        SimpleHaploid child2 = mates.get(1).copy();

        children.add(child1);
        children.add(child2);

        return children;
    }
}
