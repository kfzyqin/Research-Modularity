package ga.components.materials;

import ga.components.GRNs.DirectedEdge;
import ga.components.genes.DataGene;
import ga.components.genes.EdgeGene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Zhenyue Qin on 23/04/2017.
 * The Australian National University.
 */
public class GRNFactoryNoHiddenTarget extends GRNFactory {

    public GRNFactoryNoHiddenTarget(final int finalTargetSize, final int edgeSize) {
        super(finalTargetSize, edgeSize);
    }
}
