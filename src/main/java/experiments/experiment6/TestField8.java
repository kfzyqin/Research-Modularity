package experiments.experiment6;

import ga.components.hotspots.Hotspot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test pass list as parameter to another instance.
 * Created by zhenyueqin on 28/6/17.
 */

class Chin {
    List<Integer> grades = new ArrayList<>();

    public Chin(final List<Integer> grades) {
        this.grades = grades;
    }

    public Chin copy() {
        return new Chin(new ArrayList<>(this.grades));
    }
}

public class TestField8 {
    public static void main(String[] args) {
        List<Integer> grades1 = new ArrayList<>();
        grades1.add(81); grades1.add(68); grades1.add(89); grades1.add(96);
        Chin chin1 = new Chin(grades1);
        Chin chin2 = chin1.copy();
        chin2.grades.add(100);
        System.out.println(chin1.grades);

        Hotspot hotspot1 = new Hotspot(9, 11);
        System.out.println(hotspot1);

        Hotspot hotspot2 = hotspot1.copy();
        hotspot2.setRecombinationRateAtPosition(100, 0.06);
        System.out.println(hotspot1);
        System.out.println(hotspot2);
    }


}
