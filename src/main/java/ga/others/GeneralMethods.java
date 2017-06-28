package ga.others;

import ga.collections.Individual;
import ga.components.GRNs.DirectedEdge;
import ga.components.chromosomes.Chromosome;
import ga.components.chromosomes.SimpleDiploid;
import ga.components.chromosomes.SimpleHotspotDiploid;
import ga.components.genes.EdgeGene;
import ga.components.hotspots.Hotspot;
import ga.components.materials.GeneRegulatoryNetwork;
import ga.components.materials.SimpleMaterial;
import ga.operations.expressionMaps.DiploidEvolvedMap;
import ga.operations.expressionMaps.ExpressionMap;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by zhenyueqin on 24/6/17.
 */
public class GeneralMethods<T> {

    public static Set<String> permutation(String str) {
        return permutation("", str);
    }

    public static Set<String> permutation(String prefix, String str) {
        Set<String> set = new HashSet<>();
        int n = str.length();
        if (n == 0) {
            set.add(prefix);
            return set;
        } else {
            for (int i = 0; i < n; i++)
                set.addAll(permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n)));
        }
        return set;
    }

    public static <T> Set<List<T>> getAllListsRecursion(Set<T> elements, int lengthOfList, Set<List<T>> allCurrentLists) {
        Set<List<T>> rtn = new HashSet<List<T>>();
        for (T element : elements) {
            for (List<T> list : allCurrentLists) {
                List tmpList = new ArrayList<>(list);
                tmpList.add(0, element);
                rtn.add(tmpList);
            }
        }
        List<T> anElement = new ArrayList<>();
        for (List<T> element : rtn) {
            anElement = element;
        }
        if (anElement.size() == lengthOfList) {
            return rtn;
        } else {

        }
        return getAllListsRecursion(elements, lengthOfList, rtn);
    }

    public static <T> Set<List<T>> getAllLists(Set<T> elements, int currentLengthOfList, int lengthOfList) {
        if(currentLengthOfList == 1) {
            Set<List<T>> tmpOneElementSet = new HashSet<>(elements.size());
            for (T element : elements) {
                List<T> aOneElementList = new ArrayList<>();
                aOneElementList.add(element);
                tmpOneElementSet.add(aOneElementList);
            }
            return getAllListsRecursion(elements, lengthOfList, tmpOneElementSet);
        }
        else {
            return getAllLists(elements, currentLengthOfList - 1, lengthOfList);
        }
    }

    public static <C extends Chromosome> Individual<C> individualCloneMachine(final boolean withHotspot,
                                                                          final int networkSize,
                                                                          final int edgeNumber,
                                                                          final int hotspotSize) {
        List<EdgeGene> edgeGenes = new ArrayList<>(networkSize);
        for (int i=0; i<networkSize; i++) {
            edgeGenes.add(new EdgeGene(0));
        }

        final int edgeNumberCycle = networkSize / edgeNumber;
        boolean positive = true;

        for (int i=0; i<edgeGenes.size(); i++) {
            if (i % edgeNumberCycle == 0) {
                if (positive) {
                    edgeGenes.get(i).setValue(1);
                } else {
                    edgeGenes.get(i).setValue(-1);
                }
                positive = !positive;
            }
        }

        SimpleMaterial grn = new GeneRegulatoryNetwork(new ArrayList<>(edgeGenes));
        ExpressionMap<SimpleMaterial, SimpleMaterial> map = new DiploidEvolvedMap(grn);
        Individual<C> cloneMan;

        if (!withHotspot) {
            cloneMan = new Individual<>((C) new SimpleDiploid(grn, grn, map));
            return cloneMan.copy();
        } else {
            Hotspot hotspot = new Hotspot(hotspotSize, grn.getSize());
            cloneMan = new Individual<>((C) new SimpleHotspotDiploid(grn, grn, map, hotspot));
            return cloneMan.copy();
        }

    }
}
