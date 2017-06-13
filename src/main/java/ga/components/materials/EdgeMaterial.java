package ga.components.materials;

import ga.components.genes.EdgeGene;
import ga.components.genes.Gene;

import java.util.List;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class EdgeMaterial extends SimpleMaterial {
    protected final int networkSideSize;

    /**
     * Constructs a SimpleMaterial by a list of genes.
     *
     * @param strand a list of genes
     */
    public EdgeMaterial(List<? extends Gene> strand, final int networkSideSize) {
        super(strand);
        this.networkSideSize = networkSideSize;
        if (this.networkSideSize * this.networkSideSize != strand.size()) {
            throw new IllegalArgumentException("An edge material is wrong");
        }
    }

    @Override
    public EdgeGene getGene(int index) {
        return (EdgeGene) this.strand[index];
    }

    public EdgeGene getEdgeGeneByTwoDimensionalIndex(int x, int y) {
        return (EdgeGene) this.strand[this.networkSideSize * x + y];
    }
}
