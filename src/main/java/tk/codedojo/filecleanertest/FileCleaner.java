package tk.codedojo.filecleanertest;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FileCleaner {
    private List<String> text;

    /**
     * Default constructor. Use along with importFile.
     */
    protected FileCleaner() {
    }

    /**
     * Constructor which imports the file.
     * @param fileName file to be cleaned.
     * @throws IOException when file cannot be read.
     */
    protected FileCleaner(String fileName) throws IOException {
        this();
        this.importFile(fileName);
    }

    /**
     * Imports the file to be cleaned.
     * @param fileName name of the file to be cleaned.
     * @throws IOException when file cannot be read.
     */
    protected void importFile(String fileName) throws IOException {
        text = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.add(line);
            }
        }
    }

    /**
     * Defines which portion of the line is the key. Override to meet your use-case.
     * @param line from the input file.
     * @return key which is used to remove the duplicates.
     */
    protected abstract String getKey(String line);

    /**
     * Creates a file which contains unique records only.
     * @param outputFileName desired name for the output file.
     * @throws IOException when file cannot be written.
     */
    protected void removeDuplications(String outputFileName) throws IOException {
        Set<String> keys = new HashSet<>();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
            for (String line : text) {
                if (!keys.contains(getKey(line))) {
                    bw.write(line);
                    bw.write("\n");
                    keys.add(getKey(line));
                }
            }
        }
    }

    private Set<String> loadRecords(Set<String> records, String recordsFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(recordsFile))){
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
        }
        return records;
    }

    protected void removeLinesMatchingRecords(String recordsFile, String outputFileName) throws IOException {
        Set<String> records = new HashSet<>();
        records = loadRecords(records, recordsFile);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
            for (String line : text) {
                if (!records.contains(getKey(line))) {
                    bw.write(line);
                    bw.write("\n");
                    records.add(getKey(line));
                }
            }
        }
    }
}
