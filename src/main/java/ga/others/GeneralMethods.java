package ga.others;

import ga.components.genes.DataGene;
import ga.components.genes.EdgeGene;
import ga.components.hotspots.MatrixHotspot;
import ga.components.materials.GRN;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import org.apache.commons.math3.distribution.BinomialDistribution;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

/**
 * Created by Zhenyue Qin (秦震岳) on 24/6/17.
 * The Australian National University.
 */
public class GeneralMethods<T> {

    /**
     * Get permutations of a string
     * @param str the target string that we want to get mutations
     * @return all the permutations
     */
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

    /**
     * This returns all the permutations of n positions with a limited number of candidates,
     * e.g. if the set to choose from is {1, 2} and there are 2 positions. All the possibilities will be
     * (1, 1), (1, 2), (2, 1), (2, 2).
     * @param elements candidate elements to choose from
     * @param currentLengthOfList for recursion purpose to know when to conclude
     * @param lengthOfList the length of list that we want
     * @param <T> the type of elements
     * @return all the permutations in a list
     */
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

    public static <T> void serializeObject(T population, String file_name) {

        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        try {
            fout = new FileOutputStream(file_name);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(population);

            System.out.println("Done");

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static <T> void deserializeObject(String file_name) throws IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(file_name);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream (buffer);
        T recoveredQuarks = (T) input.readObject();

        System.out.println(recoveredQuarks);
    }

    public static void saveJSON(JSONObject obj, String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(obj.toString());
            System.out.println("Successfully Copied JSON Object to File...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GRN getGRNFromJSON(int index, String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(fileName));

        JSONObject jsonObject = (JSONObject) obj;
        String genes = ((String) jsonObject.get(Integer.toString(index))).replace("[", "").replace("]", "");
        List<String> elephantList = Arrays.asList(genes.split(", "));

        List<EdgeGene> edgeGenes = new ArrayList<>();

        for (String aGene : elephantList) {
            EdgeGene anEdgeGene = new EdgeGene(Integer.parseInt(aGene));
            edgeGenes.add(anEdgeGene);
        }
        GRN grn = new GRN(edgeGenes);
//        System.out.println(grn.getStrand().length);

        return grn;
    }

    public static MatrixHotspot getMatrixHotspotFromJSON(int index, String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(fileName));

        JSONObject jsonObject = (JSONObject) obj;
        String genes = ((String) jsonObject.get(Integer.toString(index))).replace("[", "").replace("]", "");
        List<String> elephantList = Arrays.asList(genes.split(", "));

        Map<Integer, Double> recombinationRates = new HashMap<>();

        for (int i=0; i<elephantList.size(); i++) {
            recombinationRates.put(i+1, Double.parseDouble(elephantList.get(i)));
        }

        MatrixHotspot matrixHotspot = new MatrixHotspot(8, 100, recombinationRates);
        return matrixHotspot;
    }

    public static int getEdgeNumber(final SimpleMaterial phenotype) {
        int edgeNumber = 0;
        for (int i=0; i<phenotype.getSize(); i++) {
            if ((int) phenotype.getGene(i).getValue() != 0) {
                edgeNumber += 1;
            }
        }
        return edgeNumber;
    }

    public static double getAverageNumber(int[] aList) {
        double sum = 0;
        for (double aNumber : aList) {
            sum += aNumber;
        }
        return sum / aList.length;
    }

    public static double getAverageNumber(List<Double> aList) {
        double sum = 0;
        for (double aNumber : aList) {
            sum += aNumber;
        }
        return sum / aList.size();
    }

    public static double getStandardDeviation(int[] aList) {
        double anAverage = getAverageNumber(aList);
        double tmpSum = 0;
        for (double aNumber : aList) {
            tmpSum += Math.pow((aNumber - anAverage), 2);
        }
        return Math.sqrt(tmpSum / (aList.length));
    }

    static <T> void combinationUtil(T arr[], T data[], int start,
                                int end, int index, int r, List<List<T>> storage)
    {
        // Current combination is ready to be printed, print it
        if (index == r) {
            List<T> aNewStorage = new ArrayList<>();
            for (int j=0; j<r; j++) {
                aNewStorage.add(data[j]);
            }
            storage.add(aNewStorage);
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r, storage);
        }
    }

    public static <T> List<List<T>> getCombination(T[] arr, int r) {
        // A temporary array to store all combination one by one
        T data[]=(T[]) new Object[r];
        List<List<T>> storage = new ArrayList<>();

        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, data, 0, arr.length-1, 0, r, storage);
        return storage;
    }

    static <T> void combinationUtil(T arr[], T data[], int start,
                                    int end, int index, int r, List<List<T>> storage, int limitation)
    {
        if (storage.size() > limitation) {
            return;
        }
        // Current combination is ready to be printed, print it
        if (index == r) {
            List<T> aNewStorage = new ArrayList<>();
            for (int j=0; j<r; j++) {
                aNewStorage.add(data[j]);
            }
            storage.add(aNewStorage);
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r, storage, limitation);
        }
    }

    public static <T> List<List<T>> getCombination(T[] arr, int r, int limination) {
        // A temporary array to store all combination one by one
        T data[]=(T[]) new Object[r];
        List<List<T>> storage = new ArrayList<>();

        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, data, 0, arr.length-1, 0, r, storage, limination);
        return storage;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {
        Integer[] tmp = {1, 2, 3, 4, 5};
        List<List<Integer>> a_tmp = getCombination(tmp, 2);
        for (List<Integer> e : a_tmp) {
            System.out.println(e);
        }

    }

    public static <T> List<T> getRandomElementsFromAnArray(T[] anArray, int n) {
        List<T> rtn = new ArrayList<>();

        if (anArray.length < n) {
            n = anArray.length;
        }

        final int[] ints = new Random().ints(0, anArray.length).distinct().limit(n).toArray();

        for (int anInt : ints) {
            rtn.add(anArray[anInt]);
        }
        return rtn;
    }

    public static int getCombinationNumber(int n, int r) {
        return factorial(n) / (factorial(n-r) * factorial(r));
    }

    public static int factorial(int number) {
        int result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }

    public static List<String[]> readFileLineByLine(String filePath) {
        List<String[]> lines = new ArrayList<>();
        String line;
        try {
            FileReader fileReader =
                    new FileReader(filePath);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                line = line.replace("[", "").replace("]", "");
                String[] splitLine = line.split(", ");
                lines.add(splitLine);
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            filePath + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + filePath + "'");
        }
        return lines;
    }

    public static Integer[] convertStringArrayToIntArray(String[] stringArray) {
        Integer[] rtn = new Integer[stringArray.length];
        for (int i=0; i<stringArray.length; i++) {
            rtn[i] = Integer.parseInt(stringArray[i]);
        }
        return rtn;
    }

    public static List<EdgeGene> convertArrayToList(Integer[] tmpArray1) {
        List<EdgeGene> tmpList = new ArrayList<>();
        for (Integer e : tmpArray1) {
            tmpList.add(new EdgeGene(e));
        }
        return tmpList;
    }

    public static List<EdgeGene> convertArrayToList(int[] tmpArray1) {
        List<EdgeGene> tmpList = new ArrayList<>();
        for (Integer e : tmpArray1) {
            tmpList.add(new EdgeGene(e));
        }
        return tmpList;
    }

    public static SimpleMaterial convertStringArrayToSimpleMaterial(String[] tmpArray) {
        return new SimpleMaterial(convertArrayToList(convertStringArrayToIntArray(tmpArray)));
    }

    public static void showFiles(File[] files, List<String> directories) {
        for (File file : files) {
            if (file.isDirectory()) {
//                System.out.println("Directory: " + file.getName());
                directories.add(file.getAbsolutePath());
                showFiles(file.listFiles(), directories); // Calls same method again.
            } else {
//                System.out.println("File: " + file.getName());
            }
        }
    }

    public static List<List<DataGene[][]>> getPerturbations(String path) throws IOException, ClassNotFoundException {
        String fileName= path + "/" + "Haploid-GRN-Matrix.per";
        try {
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            List<List<DataGene[][]>> perturbations = (List<List<DataGene[][]>>) ois.readObject();
            ois.close();
            return perturbations;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static SimpleMaterial getGenerationPhenotype(String path, int generation) throws IOException {
        ProcessBuilder postPB =
                new ProcessBuilder("python", "./python-tools/last_generation_phenotype_fetcher.py",
                        path, Integer.toString(generation));
        Process p2 = postPB.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(p2.getInputStream()));
        String ret = in.readLine();
        ret = ret.replace(" ", "");
        return (GeneralMethods.convertStringArrayToSimpleMaterial(ret.split(",")));
    }

    public static int getInterModuleEdgeNumber(Integer[] aGRN) {
        int grnSideSize = (int) Math.sqrt(aGRN.length);
        int middleSideIdx = (grnSideSize / 2);

        Set<Integer> partialGenes = new HashSet<>();
        for (int i=0; i<aGRN.length; i++) {
            partialGenes.add(i);
        }

        Set<Integer> nonInterGenes = new HashSet<>();
        for (int currentCrossIndex=0; currentCrossIndex<middleSideIdx; currentCrossIndex++) {
            int tmpCrossIndex = currentCrossIndex;
            while (tmpCrossIndex < middleSideIdx * grnSideSize) {
                nonInterGenes.add(tmpCrossIndex);
                tmpCrossIndex += grnSideSize;
            }
        }

        for (int currentCrossIndex=middleSideIdx; currentCrossIndex<grnSideSize; currentCrossIndex++) {
            int tmpCrossIndex = currentCrossIndex + middleSideIdx * grnSideSize;
            while (tmpCrossIndex < grnSideSize * grnSideSize) {
                nonInterGenes.add(tmpCrossIndex);
                tmpCrossIndex += grnSideSize;
            }
        }

        partialGenes.removeAll(nonInterGenes);

        int interEdgeNo = 0;
        for (Integer anIdx : partialGenes) {
            if (aGRN[anIdx] != 0) {
                interEdgeNo += 1;
            }
        }

        return interEdgeNo;

    }

    public static List<Double> getBinomialDistribution(int n, double successfulRate) {
        List<Double> rtn = new ArrayList<>();
        BinomialDistribution aBinomialDistribution = new BinomialDistribution(n, successfulRate);
        for (int i=0; i<=n; i++) {
            rtn.add(aBinomialDistribution.probability(i));
        }
        return rtn;
    }

    public static double getOriginalHammingDistance(DataGene[] attractor, int[] target) {
        int count = 0;
        for (int i=0; i<attractor.length; i++) {
            if (attractor[i].getValue() == target[i]) {
                count += 1;
            }
        }
        return attractor.length - count;
    }

    public static <T> Set<T[]> getArrayDuplicateElementNo(T[][] theArrays) {
        Set<T[]> aSet = new HashSet<>();
        for (int i=0; i<theArrays.length; i++) {
            boolean thisToAdd = true;
            for (int j=i+1; j < theArrays.length; j++) {
                if (Arrays.equals(theArrays[i], theArrays[j])) {
                    thisToAdd = false;
                    break;
                }
            }
            if (thisToAdd) {
                aSet.add(theArrays[i]);
            }
        }

        return aSet;
    }

    public static int getTwoArraysHowManyPositionsDifferent(DataGene[] array1, int[] array2) {
        if (array1.length != array2.length) {
            throw new RuntimeException("Array lengths are different when comparing. ");
        } else {
            int rtn = 0;
            for (int i=0; i<array1.length; i++) {
                if (array1[i].getValue() != (array2[i])) {
                    rtn += 1;
                }
            }
            return rtn;
        }
    }

    public static HashMap<Integer, Integer> getPerturbationNumberDistribution(DataGene[][] perturbations, final int[] target) {
        HashMap<Integer, Integer> distribution = new HashMap<>();
        for (DataGene[] aPerturbation : perturbations) {
            int aDifference = getTwoArraysHowManyPositionsDifferent(aPerturbation, target);
            if (distribution.containsKey(aDifference)) {
                distribution.put(aDifference, (distribution.get(aDifference) + 1));
            } else {
                distribution.put(aDifference, 1);
            }
        }
        return distribution;
    }

    public static int getCertainEdgeNumber(int[] aGRN, int target) {
        int count = 0;
        for (int e : aGRN) {
            if (e == target) {
                count += 1;
            }
        }
        return count;
    }

}
