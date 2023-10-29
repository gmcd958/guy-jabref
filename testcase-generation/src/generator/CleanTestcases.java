package generator;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class CleanTestcases {
    public static void cleanTestcases() {
        try {
            FileUtils.deleteDirectory(new File("C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\\src\\test\\java\\org\\jabref\\diffblue"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//    public static void getTestNames() {
//        // The root directory to start the search
//        //File rootDir = new File("C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\\build\\classes\\java\\test\\org\\jabref");
//        File rootDir = new File("C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\\src\\test\\java\\org\\jabref");
//        // Recursively traverse the subfolders and find test classes
//        findTestClasses(rootDir);
//        // Print the test method names
//        for (String testMethod : testMethods) {
//            System.out.println(testMethod);
//        }
//    }
//
//    // A helper method to recursively traverse the subfolders and find test classes
//    private static void findTestClasses (File dir) {
//        // Get all the files and subfolders in the current directory
//        File[] files = dir.listFiles();
//        if (files != null) {
//            for (File file : files) {
//                if (file.isDirectory ()) {
//                    // If the file is a subfolder, call the same method again
//                    findTestClasses (file);
//                } else {
//                    // If the file is a regular file, check if it is a diffblue test class
//                    String fileName = file.getName();
//                    if (fileName.endsWith ("DiffblueTest.java")) {
//                        // Remove the .java extension and get the class name
//                        String className = fileName.substring(0, fileName.length () - 5);
//                        // Get the package name by replacing the file separators with dots
//                        String packageName = file.getParent().replace("C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\\src\\test\\java\\", "").replace(File.separator, ".");
//                        // Get the fully qualified class name
//                        String fqcn = packageName + "." + className;
//
//                        System.out.println(fqcn);
//                        // Try to load the class and check for test methods
//                        try {
////                            Class<?> clazz = Class.forName(fqcn,
////                                    true, URLClassLoader);
//                            Class<?> clazz = Class.forName(fqcn);
//                            findTestMethods(clazz);
//                        } catch (ClassNotFoundException e) {
//                            e.printStackTrace ();
//                        }
//
//                    }
//                }
//            }
//        }
//    }
//
//    // A helper method to check if a class has any test methods and add them to the list
//    private static void findTestMethods (Class<?> clazz) {
//        // Get all the declared methods in the class
//        Method[] methods = clazz.getDeclaredMethods();
//        for (Method method : methods) {
//            // Check if the method has the @Test annotation
//            if (method.isAnnotationPresent (Test.class)) {
//                // Add the method name to the list
//                testMethods.add(clazz.getName () + "." + method.getName ());
//            }
//        }
//    }
