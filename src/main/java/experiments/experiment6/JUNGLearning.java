package experiments.experiment6;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;


public class JUNGLearning {
    public static void main(String args[]) {
        Graph graph = new MultiGraph("Tutorial 1");

        graph.addNode("A" );
        graph.addNode("B" );
        graph.addNode("C" );
        graph.addEdge("CC", "C", "C", true);
        graph.addEdge("AB", "A", "B", true);
        graph.addEdge("BA", "B", "A", true);

        graph.display();

        System.out.println(graph.getEdgeSet());
    }
}