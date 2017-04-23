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

    private final SimpleMaterial target;
    private final int networkSize;
    private final int edgeSize;

    public GeneRegulatoryNetworkFactory(int[] target, int edgeSize) {
        ArrayList<DataGene> tempTargetList = new ArrayList<>();
        for (int i=0; i<target.length; i++) {
            tempTargetList.add(new DataGene(target[i]));
        }
        this.target = new SimpleMaterial(tempTargetList);

        this.networkSize = target.length * target.length;

        this.edgeSize = edgeSize;

        if (this.edgeSize > this.networkSize) {
            throw new IllegalArgumentException("The edge size in GRN cannot be bigger than network size. ");
        }
    }

    public GeneRegulatoryNetworkFactory(SimpleMaterial target, int edgeSize) {
        this.target = target;
        this.networkSize = target.getSize() * target.getSize();
        this.edgeSize = edgeSize;
        if (this.edgeSize > this.networkSize) {
            throw new IllegalArgumentException("The edge size in GRN cannot be bigger than network size. ");
        }
    }

    public List<EdgeGene> initialiseEdges() {
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
                edgeGenes.get(edge.getLeft() * this.target.getSize() + edge.getRight()).setValue(1);
            } else {
                edgeGenes.get(edge.getLeft() * this.target.getSize() + edge.getRight()).setValue(-1);
            }
        }
        return edgeGenes;
    }

    private boolean flipACoin() {
        return 0.5 < Math.random();
    }

    public GeneRegulatoryNetwork generateGeneRegulatoryNetwork() {
        return new GeneRegulatoryNetwork(this.target, this.initialiseEdges());
    }
}
