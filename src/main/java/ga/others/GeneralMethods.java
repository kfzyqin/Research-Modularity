package ga.others;

import ga.components.genes.DataGene;
import ga.components.genes.EdgeGene;
import ga.components.hotspots.MatrixHotspot;
import ga.components.materials.GRN;
import ga.components.materials.SimpleMaterial;
import ga.operations.fitnessFunctions.FitnessFunction;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargets;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue;
import ga.operations.fitnessFunctions.GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyueSameWeight;
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

    public static double getAverageNumber(Integer[] aList) {
        double sum = 0;
        for (double aNumber : aList) {
            sum += aNumber;
        }
        return sum / aList.length;
    }

    public static double getAverageNumber(List<Double> aList) {
        double sum = 0;
        for (Double aNumber : aList) {
            sum += aNumber;
        }
        return sum / aList.size();
    }

    public static double getProportional

    public static  double getIntAverageNumber(List<Integer> aList) {
        double sum = 0;
        for (Integer aNumber : aList) {
            sum += (double) aNumber.intValue();
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

        List<Double> aList = new ArrayList<>();
        aList.add(1.0);
        aList.add(2.0);
        aList.add(3.0);
        aList.add(4.0);
        aList.add(5.0);
        System.out.println(getStDev(aList));


    }

    public static <T> List<T> convertRandomElementsFromAnArray(T[] anArray, int n) {
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

    public static Integer[] convertIntegerListToIntegerArray(List<Integer> aList) {
        Integer[] rtn = new Integer[aList.size()];
        for (int i=0; i<aList.size(); i++) {
            rtn[i] = aList.get(i);
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

    public static int[] convertStringArrayToIntArray(String[] stringArray) {
        int[] rtn = new int[stringArray.length];
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

    public static List<Integer> convertArrayToIntegerList(int[] tmpArray1) {
        List<Integer> tmpList = new ArrayList<>();
        for (Integer e : tmpArray1) {
            tmpList.add(e);
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

    public static SimpleMaterial convertIntArrayToSimpleMaterial(int[] anArray) {
        List<EdgeGene> tmpList = new ArrayList<>();
        for (Integer e : anArray) {
            tmpList.add(new EdgeGene(e));
        }
        return new SimpleMaterial(tmpList);
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

    public static int getInterModuleEdgeNumber(int[] aGRN) {
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

    public static double[] getSeparateModuleHammingDistance(DataGene[] attractor, int[] target) {
        int midPivot = target.length / 2;
        int[] firstHalfTarget = new int[midPivot];
        int[] secondHalfTarget = new int[midPivot];

        DataGene[] firstHalfAttractor = new DataGene[midPivot];
        DataGene[] secondHalfAttractor = new DataGene[midPivot];
        for (int i=0; i<midPivot; i++) {
            firstHalfTarget[i] = target[i];
            firstHalfAttractor[i] = attractor[i];
        }
        for (int i=midPivot; i<target.length; i++) {
            secondHalfTarget[i-midPivot] = target[i-midPivot];
            secondHalfAttractor[i-midPivot] = attractor[i-midPivot];
        }
        double[] rtn = new double[2];
        rtn[0] = getOriginalHammingDistance(firstHalfAttractor, firstHalfTarget);
        rtn[1] = getOriginalHammingDistance(secondHalfAttractor, secondHalfTarget);
        return rtn;
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

    public static int[] getCustomGRN() {
        int[] rtn = new int[]{
                0,	0, 0, 0, 0,	    0, 0, 0, 0, 0,
                0,	0, -1, 0, 0,    0,	0, 0, 0, 0,
                0,	0, 0, -1, 1,    0,	0, 0, 0, 0,
                0,	0, 0, 0, -1,    0,	0, 0, 0, 0,
                1,	0, 0, 0, 0,     0,	0, 0, 0, 0,
                0,	0, 0, 0, 0,     1,  -1,  1,  -1,  1,
                0,	0, 0, 0, 0,     -1,  0,  0,  0,  0,
                0,	0, 0, 0, 0,     0,  0,  0,  0,  0,
                0,	0, 0, 0, 0,     0,  0,  0,  0,  0,
                0,	0, 0, 0, 0,     0,  0,  0,  0,  0};
        return rtn;
    }

    public static int[] getSameColumnGRN(int[] aGRN) {
        int[] rtn = aGRN.clone();
        int grnSideSize = (int) Math.sqrt(aGRN.length);
        int halfGRNSideSize = (grnSideSize/2);
        for (int i=0; i<grnSideSize; i++) {
            for (int j=0; j<grnSideSize; j++) {
                if (i % 2 == 0) {
                    if (i < (grnSideSize/2)) {
                        rtn[i + j * grnSideSize] = 1;
                    } else {
                        rtn[i + j * grnSideSize] = -1;
                    }
                } else {
                    if (i < (grnSideSize/2)) {
                        rtn[i + j * grnSideSize] = -1;
                    } else {
                        rtn[i + j * grnSideSize] = 1;
                    }
                }
                if ((i >= halfGRNSideSize && j < halfGRNSideSize) || (i < halfGRNSideSize && j >= halfGRNSideSize)) {
                    rtn[i + j * grnSideSize] = 0;
                }
            }
        }
        return rtn;
    }

    public static int[] getPatternedGRN(int[] aGRN, boolean alterFirst) {
        int[] rtn = aGRN.clone();
        int grnSideSize = (int) Math.sqrt(aGRN.length);
        for (int i=grnSideSize/2; i<grnSideSize; i++) {
            for (int j=grnSideSize/2; j<grnSideSize; j++) {
                if (j % 2 == 0) {
                    if (i % 2 == 0) {
                        if (rtn[i + j * grnSideSize] != 1) {
                            rtn[i + j * grnSideSize] = 1;
                        }
                    } else {
                        if (rtn[i + j * grnSideSize] != -1) {
                            rtn[i + j * grnSideSize] = -1;
                        }
                    }
                }
                else {
                    if (i % 2 != 0) {
                        if (rtn[i + j * grnSideSize] != 1) {
                            rtn[i + j * grnSideSize] = 1;
                        }
                    } else {
                        if (rtn[i + j * grnSideSize] != -1) {
                            rtn[i + j * grnSideSize] = -1;
                        }
                    }
                }
            }
        }

        if (alterFirst) {
            for (int i=0; i<grnSideSize/2; i++) {
                for (int j=0; j<grnSideSize/2; j++) {
                    if (j % 2 == 0) {
                        if (i % 2 == 0) {
                            if (rtn[i + j * grnSideSize] != 1) {
                                rtn[i + j * grnSideSize] = 1;
                            }
                        } else {
                            if (rtn[i + j * grnSideSize] != -1) {
                                rtn[i + j * grnSideSize] = -1;
                            }
                        }
                    }
                    else {
                        if (i % 2 != 0) {
                            if (rtn[i + j * grnSideSize] != 1) {
                                rtn[i + j * grnSideSize] = 1;
                            }
                        } else {
                            if (rtn[i + j * grnSideSize] != -1) {
                                rtn[i + j * grnSideSize] = -1;
                            }
                        }
                    }
                }
            }
        }

        for (int i=(grnSideSize/2); i<grnSideSize; i++) {
            for (int j=0; j < (grnSideSize/2); j++) {
                rtn[i + j * grnSideSize] = 0;
            }
        }

        for (int i=0; i<(grnSideSize/2); i++) {
            for (int j=(grnSideSize/2); j<grnSideSize; j++) {
                rtn[i + j * grnSideSize] = 0;
            }
        }
        return rtn;
    }

    public static void printRow(int[] row) {
        for (int i : row) {
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
    }

    public static void printRow(int[] row, char sep) {
        for (int i : row) {
            System.out.print(i);
            System.out.print(sep);
        }
        System.out.println();
    }

    public static void printSquareGRN(int[] aGRN) {
        int grnSideSize = (int) Math.sqrt(aGRN.length);
        for (int i=0; i<grnSideSize; i++) {
            for (int j=0; j<grnSideSize; j++) {
                System.out.print(aGRN[i*grnSideSize + j] + ",\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int[] convertSimpleMaterialToIntArray(SimpleMaterial phenotype) {
        int[] rtn = new int[phenotype.getSize()];
        for (int i=0; i<phenotype.getSize(); i++) {
            rtn[i] = (int) phenotype.getGene(i).getValue();
        }
        return rtn;
    }

    public static void printSquareGRN(SimpleMaterial phenotype) {
        int[] rtn = convertSimpleMaterialToIntArray(phenotype);
        printSquareGRN(rtn);
    }

    public static int[] getInterModuleRemovedGRN(int[] aGRN) {
        int[] rtn = aGRN.clone();
        int grnSideSize = (int) Math.sqrt(aGRN.length);
        for (int i=(grnSideSize/2); i<grnSideSize; i++) {
            for (int j=0; j < (grnSideSize/2); j++) {
                rtn[i + j * grnSideSize] = 0;
            }
        }

        for (int i=0; i<(grnSideSize/2); i++) {
            for (int j=(grnSideSize/2); j<grnSideSize; j++) {
                rtn[i + j * grnSideSize] = 0;
            }
        }
        return rtn;
    }

    public static List<Double> evaluateSeparateModuleFitnesses(int[] aGRN, boolean toPrintCyclePath) {
//        System.out.println("a GRN: " + Arrays.toString(aGRN));
        int grnSideSize = (int) Math.sqrt(aGRN.length);
        int[] module1 = new int[(grnSideSize/2) * (grnSideSize/2)];
        int[] module2 = new int[(grnSideSize/2) * (grnSideSize/2)];

        int count=0;
        for (int i=0; i<grnSideSize/2; i++) {
            for (int j=0; j<grnSideSize/2; j++) {
                module1[count] = aGRN[i*grnSideSize + j];
                count += 1;
            }
        }

        count=0;
        for (int i=grnSideSize/2; i<grnSideSize; i++) {
            for (int j=grnSideSize/2; j<grnSideSize; j++) {
                module2[count] = aGRN[i*grnSideSize + j];
                count += 1;
            }
        }

//        System.out.println("module 2: " + Arrays.toString(module2));
        final int[] target1 = {
                1, -1, 1, -1, 1,
        };

        final int[] target2 = {
                -1, 1, -1, 1, -1
        };

        int[][] target_1_1 = {target1, target1};
        int[][] target_1_2 = {target1, target2};
        int[][] target_2_2 = {target2, target2};

        final int maxCycle = 30;

        final double perturbationRate = 0.15;
        final List<Integer> thresholds = Arrays.asList(0, 500);
        final int[] perturbationSizes = {0, 1, 2, 3, 4, 5};
        final int[] perturbationSizes_same_weight = {5};
        final double stride = 0.00;
        FitnessFunction fitnessFunction_1_1 = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                target_1_1, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);
        FitnessFunction fitnessFunction_1_2 = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                target_1_2, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);
        FitnessFunction fitnessFunction_2_2 = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue(
                target_2_2, maxCycle, perturbationRate, thresholds, perturbationSizes, stride);

        if (toPrintCyclePath) {
//            ((GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue) fitnessFunction_2_2).printCyclePath = true;
            ((GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue) fitnessFunction_1_1).printCyclePath = true;
        }

        FitnessFunction fitnessFunction_1_1_same_weight = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyueSameWeight(
                target_1_1, maxCycle, perturbationRate, thresholds, perturbationSizes_same_weight, stride);
        FitnessFunction fitnessFunction_1_2_same_weight = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyueSameWeight(
                target_1_2, maxCycle, perturbationRate, thresholds, perturbationSizes_same_weight, stride);
        FitnessFunction fitnessFunction_2_2_same_weight = new GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyueSameWeight(
                target_2_2, maxCycle, perturbationRate, thresholds, perturbationSizes_same_weight, stride);

        SimpleMaterial aNewMaterial1 = new SimpleMaterial(GeneralMethods.convertArrayToList(module1));
        SimpleMaterial aNewMaterial2 = new SimpleMaterial(GeneralMethods.convertArrayToList(module2));
        double fitness_1_1 = ((GRNFitnessFunctionMultipleTargets) fitnessFunction_1_1).evaluate(aNewMaterial1, 300);
        double fitness_1_2 = ((GRNFitnessFunctionMultipleTargets) fitnessFunction_1_2).evaluate(aNewMaterial1, 501);
        double fitness_2_1 = ((GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyue) fitnessFunction_1_2).evaluate(aNewMaterial2, 501);
        double fitness_2_2 = ((GRNFitnessFunctionMultipleTargets) fitnessFunction_2_2).evaluate(aNewMaterial2, 300);
        double fitness_2_2_material_1 = ((GRNFitnessFunctionMultipleTargets) fitnessFunction_1_1).evaluate(aNewMaterial2, 300);

        double fitness_1_1_same_weight = ((GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyueSameWeight) fitnessFunction_1_1_same_weight).evaluate(aNewMaterial1, 300);
        double fitness_1_2_same_weight = ((GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyueSameWeight) fitnessFunction_1_2_same_weight).evaluate(aNewMaterial1, 501);
        double fitness_2_1_same_weight = ((GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyueSameWeight) fitnessFunction_2_2_same_weight).evaluate(aNewMaterial2, 501);
        double fitness_2_2_same_weight = ((GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyueSameWeight) fitnessFunction_2_2_same_weight).evaluate(aNewMaterial2, 300);
        double fitness_2_2_material_1_same_weight = ((GRNFitnessFunctionMultipleTargetsAllCombinationBalanceAsymmetricZhenyueSameWeight) fitnessFunction_1_1_same_weight).evaluate(aNewMaterial2, 300);

        return Arrays.asList(fitness_1_1, fitness_2_2, fitness_2_1, fitness_2_2_material_1,
                fitness_1_1_same_weight, fitness_2_2_same_weight, fitness_2_1_same_weight, fitness_2_2_material_1_same_weight);
//        return Collections.singletonList(fitness_2_1);
    }

    public static double sum (List<Double> a){
        if (a.size() > 0) {
            double sum = 0;

            for (Double i : a) {
                sum += i;
            }
            return sum;
        }
        return 0;
    }

    public static double mean (List<Double> a){
        double sum = sum(a);
        double mean = 0;
        mean = sum / (a.size() * 1.0);
        return mean;
    }

    public static double getStDev (List<Double> a){
        double sum = 0;
        double mean = mean(a);

        for (Double i : a)
            sum += Math.pow((i - mean), 2);
        return Math.sqrt( sum / ( a.size() - 1 ) ); // sample
    }

    public static double getSimpleMaterialPositionDifference(SimpleMaterial material1, SimpleMaterial material2) {
        assert material1.getSize() == material2.getSize();
        double differences = 0;
        for (int i=0; i<material1.getSize(); i++) {
            if (material1.getGene(i).getValue() != material2.getGene(i).getValue()) {
                differences += 1;
            }
        }
        return differences;
    }

    public static double getSimpleMaterialAbsDifference(SimpleMaterial material1, SimpleMaterial material2) {
        assert material1.getSize() == material2.getSize();
        double differences = 0;
        for (int i=0; i<material1.getSize(); i++) {
            if (material1.getGene(i).getValue() != material2.getGene(i).getValue()) {
                int aDifference = Math.abs(((int) material1.getGene(i).getValue() - (int) material2.getGene(i).getValue()));
                differences += aDifference;
            }
        }
        return differences;
    }

    public static List<Integer> getInterModuleEdgeIdxes(int[] aGRN) {
        List<Integer> rtn = new ArrayList<>();
        int grnSideSize = (int) Math.sqrt(aGRN.length);
        for (int i=(grnSideSize/2); i<grnSideSize; i++) {
            for (int j=0; j < (grnSideSize/2); j++) {
                if (aGRN[(i + j * grnSideSize)] != 0) {
                    rtn.add((i + j * grnSideSize));
                }
            }
        }

        for (int i=0; i<(grnSideSize/2); i++) {
            for (int j=(grnSideSize/2); j<grnSideSize; j++) {
                if (aGRN[(i + j * grnSideSize)] != 0) {
                    rtn.add((i + j * grnSideSize));
                }
            }
        }
        return rtn;
    }

    public static List<Integer> getInterModuleNoEdgeIdxes(int[] aGRN) {
        List<Integer> rtn = new ArrayList<>();
        int grnSideSize = (int) Math.sqrt(aGRN.length);
        for (int i=(grnSideSize/2); i<grnSideSize; i++) {
            for (int j=0; j < (grnSideSize/2); j++) {
                if (aGRN[(i + j * grnSideSize)] == 0) {
                    rtn.add((i + j * grnSideSize));
                }
            }
        }

        for (int i=0; i<(grnSideSize/2); i++) {
            for (int j=(grnSideSize/2); j<grnSideSize; j++) {
                if (aGRN[(i + j * grnSideSize)] == 0) {
                    rtn.add((i + j * grnSideSize));
                }
            }
        }
        return rtn;
    }

    public static List<Integer> getIntraModuleEdgeIdxes(int[] aGRN) {
        List<Integer> rtn = new ArrayList<>();
        for (int i=0; i<aGRN.length; i++) {
            if (!isInterModuleEdge(i, aGRN) && aGRN[i] != 0) {
                rtn.add(i);
            }
        }
        return rtn;
    }

    public static List<Integer> getIntraModuleNoEdgeIdxes(int[] aGRN) {
        List<Integer> rtn = new ArrayList<>();
        for (int i=0; i<aGRN.length; i++) {
            if (!isInterModuleEdge(i, aGRN) && aGRN[i] == 0) {
                rtn.add(i);
            }
        }
        return rtn;
    }

    public static boolean isInterModuleEdge(int edgeId, int[] aGRN) {
        int[] rtn = aGRN.clone();
        int grnSideSize = (int) Math.sqrt(aGRN.length);
        for (int i=(grnSideSize/2); i<grnSideSize; i++) {
            for (int j=0; j < (grnSideSize/2); j++) {
                if (edgeId == (i + j * grnSideSize)) {
                    return true;
                }
            }
        }

        for (int i=0; i<(grnSideSize/2); i++) {
            for (int j=(grnSideSize/2); j<grnSideSize; j++) {
                if (edgeId == (i + j * grnSideSize)) {
                    return true;
                }
            }
        }
        return false;
    }


}
