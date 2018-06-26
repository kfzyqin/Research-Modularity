package learning;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import org.apache.commons.collections4.Transformer;
import tools.Modularity;

public class ModularityTest {
    public static void main(String[] args) {
        Modularity mod = new Modularity();

        DirectedGraph<Integer, String> g = new DirectedSparseGraph<Integer, String>();
        // Add some vertices. From above we defined these to be type Integer.

        g.addVertex((Integer)1);
        g.addVertex((Integer)2);
        g.addVertex((Integer)3);

        g.addVertex((Integer)4);
        g.addVertex((Integer)5);
        g.addVertex((Integer)6);

        g.addEdge("Edge-A", 1, 2); // Note that Java 1.5 auto-boxes primitives
        g.addEdge("Edge-B", 2, 3);
        g.addEdge("Edge-C", 1, 3); // Note that Java 1.5 auto-boxes primitives

        g.addEdge("Edge-D", 4, 5);
        g.addEdge("Edge-E", 4, 6); // Note that Java 1.5 auto-boxes primitives
        g.addEdge("Edge-F", 5, 6);

//        Modularity.computeModularity(g, )
    }
}
