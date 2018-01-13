package experiments.experiment6;

import ga.components.genes.DataGene;
import ga.components.materials.GRNFactoryNoHiddenTarget;
import ga.others.GeneralMethods;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Zhenyue Qin (秦震岳) on 18/7/17.
 * The Australian National University.
 */
public class TestField12 {
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        File[] files = new File("C:/").listFiles();
        showFiles(files);
    }
}
