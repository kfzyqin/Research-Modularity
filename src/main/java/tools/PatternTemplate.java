package tools;

public class PatternTemplate {
    public static int[][] getPatternTemplate() {
        int[][] pattern = new int[10][10];
        for (int i=0; i<5; i++) {
            for (int j=0; j<5; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        pattern[i][j] = 1;
                    } else {
                        pattern[i][j] = -1;
                    }
                } else {
                    if (j % 2 == 0) {
                        pattern[i][j] = -1;
                    } else {
                        pattern[i][j] = 1;
                    }
                }
            }
        }

        for (int i=5; i<10; i++) {
            for (int j=5; j<10; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        pattern[i][j] = 1;
                    } else {
                        pattern[i][j] = -1;
                    }
                } else {
                    if (j % 2 == 0) {
                        pattern[i][j] = -1;
                    } else {
                        pattern[i][j] = 1;
                    }
                }
            }
        }
        return pattern;
    }

}
