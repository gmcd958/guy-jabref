package generator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.*;

public class TestCaseGenerator {
    public static void main(String[] args) {
        if(Objects.equals(args[0], "generate"))
        {
            generateTestcases();
        }
        if(Objects.equals(args[0], "clean"))
        {
            cleanTestcases();
        }
    }

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

        String url = "https://ttcglobal.teamscale.io/api/projects/guy-jabref/test-gaps.csv";

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
        commandList.add("cmd.exe");
        commandList.add("/c");
        commandList.add("start");
        commandList.add("dcover.bat");
        commandList.add("create");

        for (int i = 50; i < 60 && i < references.size(); i++) {
            System.out.println(references.get(i));

            commandList.add(references.get(i));
        }

        commandList.add("--working-directory=\"C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\"");
        commandList.add("--test-output-dir=build/classes/java/test/org/jabref/diffblue");
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
    }

        //        ProcessBuilder processBuilder = new ProcessBuilder();
//        // set java to 17
//        processBuilder.command("C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\\diffblue-cover-cli\\java17.bat");
//
//        try {
//            Process process = processBuilder.start();
//
//            // Wait for the process to exit
//            process.waitFor();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }

//            try {
//                processBuilder.command("C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\\diffblue-cover-cli\\dcover.bat", "create", references.get(i), "--working-directory=\"C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\"", "--test-output-dir=build/classes/java/test/org/jabref/diffblue", "--skip-test-validation");
//
//                Process process = processBuilder.start();
//
//                // Capture the output stream of the process
//                InputStream is = process.getInputStream();
//                InputStreamReader isr = new InputStreamReader(is);
//                BufferedReader br = new BufferedReader(isr);
//
//                String line;
//                // Read and print the output line-by-line
//                while ((line = br.readLine()) != null) {
//                    System.out.println(line);
//                }
//
//                // Wait for the process to exit
//                process.waitFor();
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }

    private static void cleanTestcases() {
        try {
            FileUtils.deleteDirectory(new File("C:\\Users\\guy.mcdonald\\Documents\\teamscale-diffblue\\guy-jabref\\build\\classes\\java\\test\\org\\jabref\\diffblue\\org"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
