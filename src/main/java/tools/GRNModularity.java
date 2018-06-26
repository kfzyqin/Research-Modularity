package tools;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class GRNModularity {
    public static double getGRNModularity(List<Integer> aGRN) {
        Graph graph = new MultiGraph("GRN");
        int targetNumber = (int) Math.sqrt(aGRN.size());

        for (int i=0; i<targetNumber; i++) {
            graph.addNode(Integer.toString(i));
        }

        // i: Towards which that the GRN regulate
        for (int i=0; i<targetNumber; i++) {
            for (int j=0; j<targetNumber; j++) {
                int grnValue = aGRN.get(j * targetNumber + i);
                String potentialEdgeId = Integer.toString(j) + Integer.toString(i);
                if (grnValue == 1) {
                    graph.addEdge(potentialEdgeId + "+", Integer.toString(j), Integer.toString(i));
                } else if (grnValue == -1) {
                    graph.addEdge(potentialEdgeId + "-", Integer.toString(j), Integer.toString(i));
                }
            }
        }

        HashMap<Object, HashSet<Node>> aCommunity = getModularityPartition(graph, 5);
        return QinModularity.getQWithCommunities(graph, aCommunity);
    }

    public static HashMap<Object, HashSet<Node>> getModularityPartition(Graph graph, int modSize) {
        int targetNumber = graph.getNodeCount();
        int currentComId = 0;
        HashMap<Object, HashSet<Node>> aMap = new HashMap<>();
        for (int i=0; i<targetNumber; i=i+modSize) {
            List<Integer> aCom = GRNModularity.getARangedList(i, i+modSize);
            HashSet<Node> nodeCom = new HashSet<>();
            for (Integer n : aCom) {
                nodeCom.add(graph.getNode(Integer.toString(n)));
            }
            aMap.put(currentComId, nodeCom);
        }
        return aMap;
    }

    public static List<Integer> getARangedList(int from, int to) {
        return IntStream.rangeClosed(from, to-1).boxed().collect(toList());
    }


}
