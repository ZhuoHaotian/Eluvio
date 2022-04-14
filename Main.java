import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class Main {

    

    public static void main(String[] args){
        byte[][] byteArrays = new byte[10][];
        try {
            for (int i = 1; i <= 10; i++) {
                byte[] byteArray = Files.readAllBytes(Paths.get("sample.".concat(String.valueOf(i))).toAbsolutePath());
                byteArrays[i - 1] = byteArray;
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        int max = 0;
        Set<List<Integer>> positions = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == j) {
                    continue;
                }
                byte[] array1 = byteArrays[i];
                byte[] array2 = byteArrays[j];

                // for sliding array2 from the back to the front of array1 and comparing all bytew at each position
                for (int a = array1.length - 1; a >= 0; a--) {
                    int localMax = 0;
                    for (int b = 0; b < array2.length && a + b < array1.length; b++) {
                        if (array1[a] == array2[b]) {
                            localMax++;
                        } else {
                            if (localMax >= max) {
                                if (localMax > max) {
                                    max = localMax;
                                    positions.clear();
                                }
                                List<Integer> toAdd1 = new ArrayList<>();
                                toAdd1.add(i + 1);
                                toAdd1.add(a + b);
                                positions.add(toAdd1);
                                List<Integer> toAdd2 = new ArrayList<>();
                                toAdd2.add(j + 1);
                                toAdd2.add(b);
                                positions.add(toAdd2);
                            }
                            localMax = 0;
                        }
                    }
                }

                // for sliding array2 from front to back starting at the start of array1
                for (int b = 0; b < array2.length; b++) {
                    int localMax = 0;
                    for (int a = 0; a < array1.length && a + b < array2.length; a++) {
                        if (array1[a] == array2[b]) {
                            localMax++;
                        } else {
                            if (localMax >= max) {
                                if (localMax > max) {
                                    max = localMax;
                                    positions.clear();
                                }
                                List<Integer> toAdd1 = new ArrayList<>();
                                toAdd1.add(i + 1);
                                toAdd1.add(a);
                                positions.add(toAdd1);
                                List<Integer> toAdd2 = new ArrayList<>();
                                toAdd2.add(j + 1);
                                toAdd2.add(a + b);
                                positions.add(toAdd2);
                            }
                            localMax = 0;
                        }
                    }
                }
            }
        }

        System.out.println("The longest common strand is " + max + " bytes long");
        System.out.println("They are at positions: ");
        for (List<Integer> position : positions) {
            System.out.printf("File sample.%d, offset %d\n", position.get(0), position.get(1));
        }
    }
}
