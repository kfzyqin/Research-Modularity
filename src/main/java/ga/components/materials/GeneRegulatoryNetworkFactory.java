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
public class GeneRegulatoryNetworkFactory {

    private final int targetLength;
    private final int networkSize;
    private final int edgeSize;

    public GeneRegulatoryNetworkFactory(final int targetLength, int edgeSize) {
        this.targetLength = targetLength;

        this.networkSize = targetLength * targetLength;

        this.edgeSize = edgeSize;

        if (this.edgeSize > this.networkSize) {
            throw new IllegalArgumentException("The edge size in GRN cannot be bigger than network size. ");
        }
    }

    private List<EdgeGene> initializeEdges() {
        List<DirectedEdge> candidates = new ArrayList<>();
        for (int i=0; i<networkSize; i++) {
            for (int j=0; j<networkSize; j++) {
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
                edgeGenes.get(edge.getLeft() * this.targetLength + edge.getRight()).setValue(1);
            } else {
                edgeGenes.get(edge.getLeft() * this.targetLength + edge.getRight()).setValue(-1);
            }
        }
        return edgeGenes;
    }

    private boolean flipACoin() {
        return 0.5 < Math.random();
    }

    public GeneRegulatoryNetwork generateGeneRegulatoryNetwork() {
        return new GeneRegulatoryNetwork(this.initializeEdges());
    }
}
