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
    public static Graph getGRNGraph(List<Integer> aGRN) {
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
                    graph.addEdge(potentialEdgeId + "+", Integer.toString(j), Integer.toString(i), true);
                } else if (grnValue == -1) {
                    graph.addEdge(potentialEdgeId + "-", Integer.toString(j), Integer.toString(i), true);
                }
            }
        }
        return graph;
    }

    public static double[] avgMod15To50 = {-0.25185, -0.1232625, -0.0793111111111043, -0.0599, -0.04553600000000051, -0.04056388888889196, -0.03440102040816322, -0.0288109375, -0.02353641975308617, -0.02327099999999968, -0.020985123966941573, -0.01758923611111028, -0.014139349112426203, -0.015494642857142912, -0.01613844444444463, -0.01240859375, -0.010803806228373576, -0.011421141975309126, -0.011073407202215748, -0.010523250000000168, -0.00889557823129251, -0.008399586776859506, -0.00872967863894127, -0.00693350694444443, -0.007135680000000289, -0.008271449704141896, -0.007231550068587032, -0.006384630102040724, -0.005263614744351939, -0.004842444444444287, -0.006305306971904333, -0.005209765625, -0.005007943067033902, -0.004655536332180001, -0.0057611020408159805, -0.00518032407407408, -0.0054715485756025115, -0.00392700831024935, -0.005219296515450618, -0.0037343125000000137, -0.00415844735276626, -0.0028555555555555595, -0.003684451054624013, -0.0033860020661156554, -0.003433333333333227, -0.0026245982986767493, -0.0028335898596650493, -0.0018369574652777952, -0.0029276551436901227, -0.0011924000000000394};
    public static double[] maxMod15To50 = {0.0, 0.5, 0.4444444444444444, 0.5, 0.48, 0.5, 0.48979591836734704, 0.5, 0.49382716049382713, 0.5, 0.49586776859504145, 0.48611111111111105, 0.42307692307692313, 0.5, 0.43333333333333335, 0.419921875, 0.4342560553633218, 0.38888888888888884, 0.3421052631578947, 0.38, 0.3390022675736962, 0.362603305785124, 0.32514177693761814, 0.33333333333333337, 0.3767999999999999, 0.3069526627218935, 0.2770919067215363, 0.2857142857142857, 0.31807372175980975, 0.28, 0.27419354838709675, 0.28076171875, 0.2713498622589531, 0.24913494809688574, 0.21387755102040817, 0.220679012345679, 0.2293644996347699, 0.23545706371191144, 0.21794871794871795, 0.24499999999999988, 0.23170731707317072, 0.23781179138321995, 0.24310438074634932, 0.20428719008264462, 0.22716049382716053, 0.2171550094517959, 0.19646899049343614, 0.18728298611111105, 0.1863806747188671, 0.15980000000000005};
    public static double[] minMod15To50 = {-0.5, -0.5, -0.5, -0.5, -0.5, -0.5, -0.5, -0.5, -0.5, -0.5, -0.4132231404958676, -0.5, -0.42603550295858, -0.5, -0.4355555555555556, -0.439453125, -0.38235294117647056, -0.3888888888888889, -0.39473684210526316, -0.36124999999999996, -0.35827664399092957, -0.33471074380165294, -0.3412098298676748, -0.31336805555555547, -0.33999999999999997, -0.31434911242603547, -0.31550068587105623, -0.308673469387755, -0.33293697978596903, -0.3022222222222222, -0.278876170655567, -0.28173828125, -0.23140495867768596, -0.2945501730103805, -0.27142857142857146, -0.2534722222222221, -0.23557341124908693, -0.23822714681440432, -0.24490466798159105, -0.20499999999999996, -0.23200475907198093, -0.2142857142857143, -0.20443482963764203, -0.20480371900826455, -0.211358024691358, -0.19659735349716445, -0.2021276595744681, -0.17013888888888873, -0.19575177009579336, -0.18320000000000008};

    public static double getNormedMod(double aMod, int edgeNum) {
        double avgMod = avgMod15To50[edgeNum - 1];
        double maxMod = maxMod15To50[edgeNum - 1];
        double minMod = minMod15To50[edgeNum - 1];

//        System.out.println("a mod" + aMod);
//        System.out.println("max mod: " + maxMod);
//        System.out.println("edge num: " + edgeNum);

        return (aMod - avgMod) / (maxMod - avgMod);
    }

    public static double getGRNModularity(List<Integer> aGRN) {
        Graph graph = getGRNGraph(aGRN);
        HashMap<Object, HashSet<Node>> aCommunity = getModularityPartition(graph, 5);
        return QinModularity.getQWithCommunities(graph, aCommunity);
    }



    public static double getGRNModularity(Graph aGRN) {
        HashMap<Object, HashSet<Node>> aCommunity = getModularityPartition(aGRN, 5);
        return QinModularity.getQWithCommunities(aGRN, aCommunity);
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
            currentComId += 1;
        }
        return aMap;
    }

    public static List<Integer> getARangedList(int from, int to) {
        return IntStream.rangeClosed(from, to-1).boxed().collect(toList());
    }


}
