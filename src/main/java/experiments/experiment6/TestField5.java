package experiments.experiment6;

import ga.components.genes.DataGene;
import ga.components.genes.Gene;
import ga.components.materials.SimpleMaterial;

import java.util.*;

/**
 * Created by zhenyueqin on 26/6/17.
 */
public class TestField5 {
    public static void main(String[] args) {
        Set<SimpleMaterial> set = new HashSet<>();

        Map<SimpleMaterial, Double> map = new HashMap<>();

        Integer[] integers1 = {1, -1, 1, -1, 1};
        Integer[] integers2 = {1, -1, 1, -1, 1};

        Integer[] test1 = {1, 2, 3, 4, 5};
        Integer[] test2 = {1, 2, 3, 4, 5};

        Set<List<Integer>> arraySet = new HashSet<>();
        arraySet.add(Arrays.asList(test1));
        arraySet.add(Arrays.asList(test2));

        System.out.println(arraySet);

        int a = 3;

        System.out.println(Arrays.hashCode(test1));
        System.out.println(Arrays.hashCode(test2));

        System.out.println("=====");
        Gene<Integer> gene1 = new DataGene(1);
        Gene<Integer> gene2 = new DataGene(1);
        System.out.println(gene1.hashCode());
        System.out.println(gene2.hashCode());

        SimpleMaterial material1 = new SimpleMaterial(convertArrayToGeneList(integers1));
        SimpleMaterial material2 = new SimpleMaterial(convertArrayToGeneList(integers2));

        set.add(material1);
        set.add(material2);

        map.put(material1, 0.1);
        map.put(material1, 0.2);
        System.out.println(map.get(material2));


        System.out.println(map);

        System.out.println(set);
        System.out.println(material1.equals(material2));

        System.out.println(material1.hashCode());
        System.out.println(material2.hashCode());

    }

    public static List<Gene<Integer>> convertArrayToGeneList(Integer[] integers) {
        List<Gene<Integer>> rtn = new ArrayList<>();
        for (Integer e : integers) {
            rtn.add(new DataGene(e));
        }
        return rtn;
    }
}
