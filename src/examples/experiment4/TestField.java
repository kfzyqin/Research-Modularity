package examples.experiment4;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Zhenyue Qin on 22/04/2017.
 * The Australian National University.
 */
public class TestField {
    public static void main(String[] args) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter("yourfile.csv"), ',');
        // feed in your array (or convert your data to an array)
        String[] entries = "first#second#third".split("#");
        writer.writeNext(entries);
        String[] test = "chin#liu#zheng".split("#");
        writer.writeNext(test);
        writer.close();
    }
}
