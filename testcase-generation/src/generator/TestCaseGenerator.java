package generator;

import java.io.IOException;
import java.util.*;

public class TestCaseGenerator {
    public static void main(String[] args) {
        if (Objects.equals(args[0], "generate")) {
            try {
                GenerateTestcases.generateTestcases();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (Objects.equals(args[0], "run")) {
            try {
                RunTestcases.runTestcases();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (Objects.equals(args[0], "clean")) {
            try {
                CleanTestcases.cleanTestcases();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
