package ga.components.materials;

import ga.components.GRNs.DirectedEdge;
import ga.components.genes.EdgeGene;
import tools.PatternTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

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
        List<DirectedEdge> candidates = new ArrayList<>();
        for (int i=0; i<manifestTargetSize; i++) {
            for (int j=0; j<manifestTargetSize; j++) {
                candidates.add(new DirectedEdge(i, j));
            }
        }
        final int[] edgeIndices =
                ThreadLocalRandom.current().ints(0, this.networkSize).distinct().limit(edgeSize).toArray();

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

    public List<EdgeGene> initializeModularizedEdges(final int modularIndex, final boolean toUsePattern) {
        if (edgeSize > networkSize / 2) {
            throw new IllegalArgumentException("Edge size must be less than half of network size. ");
        }
        List<DirectedEdge> candidates1 = new ArrayList<>();
        for (int i=0; i<modularIndex; i++) {
            for (int j=0; j<modularIndex; j++) {
                candidates1.add(new DirectedEdge(i, j));
            }
        }

        List<DirectedEdge> candidates2 = new ArrayList<>();
        for (int i=modularIndex; i<manifestTargetSize; i++) {
            for (int j=modularIndex; j<manifestTargetSize; j++) {
                candidates2.add(new DirectedEdge(i, j));
            }
        }

        final int[] edgeIndices1 =
                ThreadLocalRandom.current().ints(0, candidates1.size()).
                        distinct().limit((edgeSize / 2)).toArray();

        final int[] edgeIndices2 =
                ThreadLocalRandom.current().ints(0, candidates2.size()).
                        distinct().limit((edgeSize / 2)).toArray();

        List<DirectedEdge> edges = new ArrayList<>();
        for (int edgeIndex : edgeIndices1) {
            edges.add(candidates1.get(edgeIndex));
        }
        for (int edgeIndex : edgeIndices2) {
            edges.add(candidates2.get(edgeIndex));
        }

        List<EdgeGene> edgeGenes = new ArrayList<>(this.networkSize);
        for (int i=0; i<this.networkSize; i++) {
            edgeGenes.add(new EdgeGene(0));
        }

        if (toUsePattern) {
            for (DirectedEdge edge : edges) {
                edgeGenes.get(edge.getLeft() * this.manifestTargetSize + edge.getRight()).setValue(PatternTemplate.getPatternTemplate()[edge.getLeft()][edge.getRight()]);
            }
        } else {
            for (DirectedEdge edge : edges) {
                if (this.flipACoin()) {
                    edgeGenes.get(edge.getLeft() * this.manifestTargetSize + edge.getRight()).setValue(1);
                } else {
                    edgeGenes.get(edge.getLeft() * this.manifestTargetSize + edge.getRight()).setValue(-1);
                }
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

    public GRN generateModularizedGeneRegulatoryNetwork(final int moduleIndex, final boolean toUsePattern) {
        return new GRN(this.initializeModularizedEdges(moduleIndex, toUsePattern));
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
