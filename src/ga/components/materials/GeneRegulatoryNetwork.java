package ga.components.materials;

import ga.components.genes.EdgeGene;
import ga.components.genes.Gene;

import java.util.List;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class GeneRegulatoryNetwork extends EdgeMaterial {
    private SimpleMaterial target;

    /**
     * Constructs a SimpleMaterial by a list of genes.
     *
     */
    public GeneRegulatoryNetwork(SimpleMaterial target, List<EdgeGene> edgeList, int maxCycle) {
        super(edgeList, target.getSize());
        this.target = target;

    }

    public void perturb(double mu) {
        for (int i=0; i<this.target.getSize() * this.target.getSize(); i++) {
            if (Math.random() <= mu) {
                this.toMutate(i);
            }
        }
    }

    public void toMutate(int i) {
        if ((Integer) this.strand[i].getValue() == -1) {
            this.strand[i].setValue(Math.random() < 0.5 ? 0 : 1);
        } else {
            this.strand[i].setValue(Math.random() < 0.5 ? (1-(int)this.strand[i].getValue()) : -1);
        }
    }
}
