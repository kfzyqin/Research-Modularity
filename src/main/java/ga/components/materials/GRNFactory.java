package ga.components.materials;

import ga.components.GRNs.DirectedEdge;
import ga.components.genes.EdgeGene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhenyueqin on 25/6/17.
 */
public abstract class GRNFactory {
    protected final int manifestTargetSize;
    protected final int networkSize;
    protected final int edgeSize;

    public GRNFactory(final int manifestTargetSize, int edgeSize) {
        this.manifestTargetSize = manifestTargetSize;
        this.networkSize = manifestTargetSize * manifestTargetSize;
        this.edgeSize = edgeSize;
        if (this.edgeSize > this.networkSize) {
            throw new IllegalArgumentException("The edge size in GRN cannot be bigger than network size. ");
        }
    }

    protected List<EdgeGene> initializeEdges() {
        //todo: potential bug that directed edge's size is too big
        List<DirectedEdge> candidates = new ArrayList<>();
        for (int i=0; i<manifestTargetSize; i++) {
            for (int j=0; j<manifestTargetSize; j++) {
                candidates.add(new DirectedEdge(i, j));
            }
        }
        final int[] edgeIndices = new Random().ints(0, this.networkSize).distinct().limit(edgeSize).toArray();
        List<DirectedEdge> edges = new ArrayList<>();

        for (int edgeIndex : edgeIndices) {
            edges.add(candidates.get(edgeIndex));
        }

        List<EdgeGene> edgeGenes = new ArrayList<>(this.networkSize);
        for (int i=0; i<this.networkSize; i++) {
            edgeGenes.add(new EdgeGene(0));
        }

        for (DirectedEdge edge : edges) {
            if (this.flipACoin()) {
                edgeGenes.get(edge.getLeft() * this.manifestTargetSize + edge.getRight()).setValue(1);
            } else {
                edgeGenes.get(edge.getLeft() * this.manifestTargetSize + edge.getRight()).setValue(-1);
            }
        }
        return edgeGenes;
    }

    private boolean flipACoin() {
        return 0.5 < Math.random();
    }

    public GRN generateGeneRegulatoryNetwork() {
        return new GRN(this.initializeEdges());
    }

    public int getManifestTargetSize() {
        return this.manifestTargetSize;
    }

    public int getEdgeSize() {
        return this.edgeSize;
    }

    public int getNetworkSize() {
        return this.networkSize;
    }
}
