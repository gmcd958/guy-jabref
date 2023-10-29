package generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RunTestcases {

    public static void runTestcases() {
        List<String> testClassNames = getTestClassNames();

        runTestsAndUpload(testClassNames);
    }

    private static List<String> getTestClassNames() {
        List<String> testClasses = new ArrayList<>();

        String fileName = "targetedTests"; // the name of the file to read
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName)); // read all lines into a list
            String[] array = lines.toArray(new String[0]); // convert the list to an array
            for (String line : array) { // loop through the array and print each line
                testClasses.add(line + "DiffblueTest");
            }
        } catch (IOException e) { // handle any exceptions
            e.printStackTrace();
        }

        return testClasses;
    }

    private static void runTestsAndUpload(List<String> testClassNames) {
        List<String> commandList = new ArrayList<>();

        commandList.add("cmd.exe");
        commandList.add("/c");
        commandList.add("start");
        commandList.add("gradlew");
        commandList.add("clean");
        commandList.add("tiaTests");
        commandList.add("--tests");

        System.out.println(testClassNames.size());

        for (String testClass : testClassNames) {
            System.out.println(testClass);
            commandList.add(testClass);
        }

        commandList.add("teamscaleReportUpload");
        commandList.add("--continue");

        // run targeted diffblue tests

        try {
            String[] commands = commandList.toArray(new String[0]);
            File workingDir = new File("C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref");
            Process process = Runtime.getRuntime().exec(commands, null, workingDir);

            int exitCode = process.waitFor();
            System.out.println("Process exited with code " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
