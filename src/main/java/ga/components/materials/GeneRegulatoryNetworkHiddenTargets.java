package ga.components.materials;

import ga.components.genes.EdgeGene;
import ga.components.genes.Gene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhenyueqin on 25/6/17.
 */
public class GeneRegulatoryNetworkHiddenTargets extends GeneRegulatoryNetwork {
    private final int manifestTargetSize;

    /**
     * Constructs a SimpleMaterial by a list of genes.
     *
     * @param edgeList a list of edge genes
     */
    public GeneRegulatoryNetworkHiddenTargets(List<EdgeGene> edgeList,
                                              final int manifestTargetSize) {
        super(edgeList);
        this.manifestTargetSize = manifestTargetSize;
    }

    public int getManifestTargetSize() {
        return manifestTargetSize;
    }

    @Override
    public GeneRegulatoryNetworkHiddenTargets copy() {
        List<EdgeGene> strand = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            strand.add((EdgeGene)this.strand[i].copy());
        return new GeneRegulatoryNetworkHiddenTargets(strand, this.manifestTargetSize);
    }
}
