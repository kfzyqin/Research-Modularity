package experiments.experiment6;

import ga.components.materials.GRNFactoryNoHiddenTarget;

/**
 * Created by Zhenyue Qin (秦震岳) on 18/7/17.
 * The Australian National University.
 */
public class TestField12 {
    public static void main(String[] args) {
        GRNFactoryNoHiddenTarget grnFactory = new GRNFactoryNoHiddenTarget(4, 6);
        System.out.println(grnFactory.initializeModularizedEdges(2));

    }
}
