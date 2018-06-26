package learning;

import org.graphstream.algorithm.ConnectedComponents;
import tools.QinModularity;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


public class JUNGLearning {
    public static void main(String args[]) {
        Graph graph = new MultiGraph("Tutorial 1");

        Node A = graph.addNode("A");
        Node B = graph.addNode("B");
        Node C = graph.addNode("C");

        Node D = graph.addNode("D");
        Node E = graph.addNode("E");
        Node F = graph.addNode("F");

        Node G = graph.addNode("G");
        Node H = graph.addNode("H");
        Node I = graph.addNode("I");

        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("AC", "A", "C");

        graph.addEdge("AD", "A", "D");

        graph.addEdge("DE", "D", "E");
        graph.addEdge("DF", "D", "F");
        graph.addEdge("EF", "E", "F");

        graph.addEdge("EG", "E", "G");

        graph.addEdge("GH", "G", "H");
        graph.addEdge("GI", "G", "I");
        graph.addEdge("HI", "H", "I");

        ConnectedComponents cc = new ConnectedComponents(graph);
        cc.init(graph);

        QinModularity mod = new QinModularity("module");

        mod.init(graph);

        cc.setCountAttribute("module");
        cc.compute();


//        graph.display();
        HashMap<Object, HashSet<Node>> aMap = new HashMap<>();
        aMap.put(0, new HashSet<>(Arrays.asList(A, B, C)));
        aMap.put(1, new HashSet<>(Arrays.asList(D, E, F)));
        aMap.put(2, new HashSet<>(Arrays.asList(G, H, I)));
        System.out.println(QinModularity.getQWithCommunities(graph, aMap));
    }
}