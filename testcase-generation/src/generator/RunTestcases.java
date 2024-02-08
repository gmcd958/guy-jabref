package generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RunTestcases {

    public static void runTestcases() {
        runTestsAndUpload();
    }

    private static void runTestsAndUpload() {
        List<String> commandList = new ArrayList<>();

        commandList.add("cmd.exe");
        commandList.add("/c");
        commandList.add("start");
        commandList.add("gradlew");
        commandList.add("clean");
        commandList.add(":tiaTests");

        commandList.add("--tests=org.jabref.*DiffblueTest");
        commandList.add(":teamscaleReportUpload");
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
