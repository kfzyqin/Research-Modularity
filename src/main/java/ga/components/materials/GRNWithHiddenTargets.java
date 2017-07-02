package ga.components.materials;

import ga.components.genes.EdgeGene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenyueqin on 25/6/17.
 */
public class GRNWithHiddenTargets extends GRN {
    private final int manifestTargetSize;

    /**
     * Constructs a SimpleMaterial by a list of genes.
     *
     * @param edgeList a list of edge genes
     */
    public GRNWithHiddenTargets(List<EdgeGene> edgeList,
                                final int manifestTargetSize) {
        super(edgeList);
        this.manifestTargetSize = manifestTargetSize;
    }

    public int getManifestTargetSize() {
        return manifestTargetSize;
    }

    @Override
    public GRNWithHiddenTargets copy() {
        List<EdgeGene> strand = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            strand.add((EdgeGene)this.strand[i].copy());
        return new GRNWithHiddenTargets(strand, this.manifestTargetSize);
    }
}
