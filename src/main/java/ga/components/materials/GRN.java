package ga.components.materials;

import ga.components.genes.EdgeGene;
import tools.GRNModularity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class GRN extends EdgeMaterial implements Serializable {
    private double modularity = -10;


    /**
     * Constructs a SimpleMaterial by a list of genes.
     *
     */
    public GRN(List<EdgeGene> edgeList) {
        super(edgeList);
    }

    public List<Integer> getIntGRNList() {
        List<Integer> rtn = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            rtn.add(((EdgeGene) this.strand[i].copy()).getValue());
        }
        return rtn;
    }

    public double getGRNModularity() {
        if (this.modularity == -10) {
            this.modularity = this.calculateGRNModularity();
        }
        return this.modularity;
    }

    private double calculateGRNModularity() {
        return GRNModularity.getGRNModularity(this.getIntGRNList());
    }

    @Override
    public GRN copy() {
        List<EdgeGene> strand = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            strand.add((EdgeGene)this.strand[i].copy());
        return new GRN(strand);
    }
}
