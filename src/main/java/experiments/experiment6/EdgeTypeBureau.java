package experiments.experiment6;

import ga.components.materials.SimpleMaterial;
import ga.others.GeneralMethods;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static ga.others.GeneralMethods.printRow;

public class EdgeTypeBureau {
    public static void main(String[] args) {
        String fileName = "/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/fixed-record-zhenyue-balanced-combinations-p00";
        int[][] plusNo = new int[10][10];
        int[][] minusNo = new int[10][10];
        int[][] zerosNo = new int[10][10];
        int[][] edgeNo = new int[10][10];

        File[] directories = new File(fileName).listFiles(File::isDirectory);

        for (File aDirectory : directories) {
            try {
                String aModFile = aDirectory + "/" + "phenotypes_fit.list";
                List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);

                String[] lastGRNString = lines.get(lines.size() - 1);
                SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lastGRNString);

                for (int i=0; i<aMaterial.getSize(); i++) {
                    if ((int) aMaterial.getGene(i).getValue() == 1) {
                        plusNo[i/10][i%10] += 1;
                    } else if ((int) aMaterial.getGene(i).getValue() == 0) {
                        zerosNo[i/10][i%10] += 1;
                    } else if ((int) aMaterial.getGene(i).getValue() == -1) {
                        minusNo[i/10][i%10] += 1;
                    }

                    if ((int) aMaterial.getGene(i).getValue() != 0) {
                        edgeNo[i/10][i%10] += 1;
                    }

                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Array out of bound caught! ");
            }
        }

        System.out.println("Plus no: ");
        for(int[] row : plusNo) {
            printRow(row);
        }

        System.out.println("Minus no: ");
        for(int[] row : minusNo) {
            printRow(row);
        }

        System.out.println("Zeros no: ");
        for(int[] row : zerosNo) {
            printRow(row);
        }

        System.out.println("Edge no: ");
        for(int[] row : edgeNo) {
            printRow(row, ',');
        }
    }
}
