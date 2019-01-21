package experiments.experiment6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static ga.others.GeneralMethods.printRow;

public class EdgeTypeBureau {
    public static void main(String[] args) {
        String fileName = "/Users/qin/Portal/generated-outputs/fixed-record-zhenyue-balanced-combinations-elite-p001/volcanoe_grns.txt";
        int[][] plusNo = new int[10][10];
        int[][] minusNo = new int[10][10];
        int[][] zerosNo = new int[10][10];

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String aStrGRN = line.replace("[", "").replace("]", "").replace(" ", "");
                int[] aIntGRN = Arrays.stream(aStrGRN.split(",")).mapToInt(Integer::parseInt).toArray();
                int grnSideSize = (int) Math.sqrt(aIntGRN.length);

                for (int i=0; i<grnSideSize; i++) {
                    for (int j=0; j<grnSideSize; j++) {
                        if (aIntGRN[i*grnSideSize + j] == 1) {
                            plusNo[i][j] += 1;
                        } else if (aIntGRN[i*grnSideSize + j] == -1) {
                            minusNo[i][j] += 1;
                        } else if (aIntGRN[i*grnSideSize + j] == 0) {
                            zerosNo[i][j] += 1;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    }
}
