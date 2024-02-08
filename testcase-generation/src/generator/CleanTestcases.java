package generator;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class CleanTestcases {
    public static void cleanTestcases() {
        try {
            deleteDiffblueTests();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteDiffblueTests() {
        // The root directory to start the search
        //File rootDir = new File("C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\\build\\classes\\java\\test\\org\\jabref");
        File rootDir = new File("C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\\src\\test\\java\\org\\jabref");
        // Recursively traverse the subfolders and find test classes
        findTestClasses(rootDir);
    }

    // A helper method to recursively traverse the subfolders and find test classes
    private static void findTestClasses (File dir) {
        // Get all the files and subfolders in the current directory
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory ()) {
                    // If the file is a subfolder, call the same method again
                    findTestClasses (file);
                } else {
                    // If the file is a regular file, check if it is a diffblue test class
                    String fileName = file.getName();
                    if (fileName.endsWith ("DiffblueTest.java")) {
                        boolean value = file.delete();
                        if(value) {
                            System.out.println(fileName + " Deleted");
                        } else {
                            System.out.println("File doesn't exit");
                        }
                    }
                }
            }
        }
    }
}


