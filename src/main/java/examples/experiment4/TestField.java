package examples.experiment4;

import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */

public class TestField {
    int x = 10;

    public static void main(String[] args) throws IOException {
//        CSVWriter writer = new CSVWriter(new FileWriter("yourfile.csv"), ',');
//        // feed in your array (or convert your data to an array)
//
//        String[] entries = "first#second#third".split("#");
//        writer.writeNext(entries);
//        String[] test = "chin#liu#zheng".split("#");
//        writer.writeNext(test);
//        writer.close();

//      System.out.println(isPalindrome("12321"));

        Process p = Runtime.getRuntime().exec("python test1.py");
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        int ret = new Integer(in.readLine()).intValue();
//        System.out.println("value is : "+ret);
    }

}


