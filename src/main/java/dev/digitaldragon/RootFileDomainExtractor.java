package dev.digitaldragon;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class RootFileDomainExtractor {
    public static void main(String[] args) {
        // Directory path where your text files are located
        String directoryPath = "todo";

        // Process each text file in the directory
        try {
            // List all files in the directory
            File directory = new File(directoryPath);
            File[] files = directory.listFiles();
            if (files == null)
                return;

            // Create a new file with the extracted domain names
            String outputPath = "done.txt";
            FileWriter writer = new FileWriter(outputPath);


            // Iterate over each file
            for (File file : files) {
                Set<String> domains = new HashSet<>();
                int i = 0;
                // Read the file using a BufferedReader
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;



                // Process each line in the file
                while ((line = reader.readLine()) != null) {
                    // Extract domain name from the line
                    String domainName = extractDomainName(line);

                    if (domainName != null) {
                        System.out.println(domainName);
                        if (!domains.contains(domainName))
                            writer.write(domainName + System.lineSeparator());
                        domains.add(domainName);
                    }
                    i++;

                    if (i > 1000000) {
                        domains = new HashSet<>();
                        //prevent heap overflow. may lead to some duplicates, but not enough that I have to care. (i'm looking at you, .com zone.)
                    }
                }

                // Close the BufferedReader
                reader.close();
            }
            writer.close();
            System.out.println("Output file finished writing successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String extractDomainName(String line) {
        String[] parts = line.split("\\.\\t", 2);
        return parts.length > 0 ? parts[0] : "";
    }
}