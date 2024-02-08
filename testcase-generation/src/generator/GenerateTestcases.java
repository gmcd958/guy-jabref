package generator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.HashSet;

public class GenerateTestcases {
    public static void generateTestcases()
    {
        getTeamscaleData();

        List<String> references = convertCSV();

        activateDiffblue();

        sendTeamscaleDataToDiffblue(references);

    }

    private static void getTeamscaleData()
    {
        // Create the username:password string and encode it to Base64
        String auth = "guywmcd" + ":" + "nXTnEGxQT2gfnNw87AoF7es36cbt4Csx";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);

        // String url = "https://ttcglobal.teamscale.io/api/projects/guy-jabref/test-gaps.csv";
        String url = "https://ttcglobal.teamscale.io/api/projects/guy-jabref/test-gaps.csv?all-partitions=true&uniform-path=src%2Fmain%2Fjava%2Forg%2Fjabref%2Flogic%2Fjournals&auto-select-branch=true";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);

            // Add the Authorization header with Basic authentication details
            request.setHeader("Authorization", authHeader);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try (InputStream inputStream = entity.getContent();
                         FileOutputStream outputStream = new FileOutputStream("output.csv")) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> convertCSV()
    {
        List<String[]> testGapData = new ArrayList<>();

        try (Reader in = new FileReader(new File("output.csv").getAbsolutePath())) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(';')
                    .withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord record : records) {
                String columnOne = record.get(0);  // get the value in the first column
                String columnTwo = record.get(1);  // get the value in the second column
                if (columnOne.contains("src/main/java/org/jabref"))
                {
                    if (!columnOne.contains("buildSrc"))
                        testGapData.add(new String[]{columnOne, columnTwo});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transformPathsToReferences(testGapData);
    }

    private static List<String> transformPathsToReferences(List<String[]> testGapData) {
        List<String> references = new ArrayList<>();

        for (String[] record : testGapData)
        {
            // Input: List in the format [path, method]
            String path = record[0];
            String method = record[1];

            // Remove prefix and file extension from the path
            String cleanedPath = path.replace("src/main/java/", "").replace(".java", "");

            // Replace slashes with dots
            String pathWithDots = cleanedPath.replace("/", ".");

            // Concatenate to get the final string in desired format
            String reference = pathWithDots + "." + method;

            references.add(reference);
        }

        return references;
    }

    private static void activateDiffblue() {
        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command("C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\\diffblue-cover-cli\\dcover.bat", "activate", "H35R-LD2Z-VLAJ-YWCO");

        try {
            Process process = processBuilder.start();

            // Wait for the process to exit
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sendTeamscaleDataToDiffblue(List<String> references) {
        List<String> commandList = new ArrayList<>();

        List<String> targetedTestMethods = new ArrayList<>();

        commandList.add("cmd.exe");
        commandList.add("/c");
        commandList.add("start");
        commandList.add("dcover.bat");
        commandList.add("create");

        System.out.println(references.size());

        for (int i = 0; i < 20 && i < references.size(); i++) {
            System.out.println(references.get(i));
            commandList.add(references.get(i));
        }

//        for (String reference : references) {
//            System.out.println(reference);
//            commandList.add(reference);
//        }

        commandList.add("--working-directory=\"C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\"");
        // commandList.add("--test-output-dir=src/test/java/org/jabref/diffblue");
        commandList.add("--skip-test-validation");
        // run targeted diffblue generation

        try {
            String[] commands = commandList.toArray(new String[0]);
            File workingDir = new File("C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\\diffblue-cover-cli");
            Process process = Runtime.getRuntime().exec(commands, null, workingDir);

            int exitCode = process.waitFor();
            System.out.println("Process exited with code " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Write unique classes into a files
        try {
            // Create a hash set to store the unique lines
            HashSet<String> uniqueLines = new HashSet<>();

            for (String method : targetedTestMethods) {
                    int index = method.lastIndexOf("."); // find the index of the last dot
                    if (index != -1) { // if the dot exists
                        method = method.substring(0, index); // remove everything after the dot
                        uniqueLines.add(method);
                    }
                }

                // Create a FileWriter object
                FileWriter fileWriter = new FileWriter("targetedTests");

                // Wrap the FileWriter with a BufferedWriter
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                // Loop over the array and write each element to a new line
                for (String testClass : uniqueLines) {
                    // Write the string to the file
                    bufferedWriter.write(testClass);

                    // Write a new line character
                    bufferedWriter.newLine();
                }

                // Close the BufferedWriter
                bufferedWriter.close();
            } catch(IOException e){
                // Handle any exceptions
                e.printStackTrace();
            }
    }
}
