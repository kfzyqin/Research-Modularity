package tools;

import ga.components.materials.SimpleMaterial;
import ga.others.GeneralMethods;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FolderDeleteHelper {
    public static void main(String[] args) throws IOException {
        String targetPath = "/home/zhenyue-qin/Research/Project-Maotai-Modularity/jars/generated-outputs/tournament-100";

        File[] directories = new File(targetPath).listFiles(File::isDirectory);
        List<String> abnormalPaths = new ArrayList<>();

        List<Double> newFitnesses = new ArrayList<>();
        for (File aDirectory : directories) {
            try {
                System.out.println("a directory: " + aDirectory);
                String aModFile = aDirectory + "/" + "phenotypes_fit.list";
                List<String[]> lines = GeneralMethods.readFileLineByLine(aModFile);
                String[] lastGRNString = lines.get(lines.size() - 1);
                SimpleMaterial aMaterial = GeneralMethods.convertStringArrayToSimpleMaterial(lastGRNString);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Deleting " + aDirectory);
                FileUtils.deleteDirectory(aDirectory);
                abnormalPaths.add(aDirectory.toString());
            }
        }
//        System.out.println(abnormalPaths);
    }
}
