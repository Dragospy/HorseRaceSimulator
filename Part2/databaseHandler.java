package Part2;

import java.io.*;
import java.util.*;

public class databaseHandler {
    private final String filePath;
    private final String delimiter = ",";

    public databaseHandler(String filePath) {
        this.filePath = filePath;

        // Create the folder and file if they don't exist
        File file = new File(filePath);
        File parentDir = file.getParentFile(); // get the "database" folder

        if (parentDir != null && !parentDir.exists()) {
            boolean dirCreated = parentDir.mkdirs();
            if (dirCreated) {
                System.out.println("Created missing folder: " + parentDir.getAbsolutePath());
            }
        }

        if (!file.exists()) {
            try {
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    System.out.println("Created missing database file: " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                System.out.println("Could not create missing file" + e);
            }
        }
    }

    // Insert a new record (a list of fields)
    public void insert(List<String> record) {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println(String.join(delimiter, record));

        } catch (IOException e) {
            System.out.println("Could not insert into db, error: " + e);
        }
    }

    // Read all records
    public List<List<String>> readAll() {
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(delimiter, -1); // -1 to keep empty fields
                records.add(Arrays.asList(fields));
            }

        } catch (IOException e) {
            System.out.println("Could not read db, error: " + e);
        }

        return records;
    }

    // Delete records matching a condition
    public void deleteIf(int fieldIndex, String matchValue) {
        List<List<String>> allRecords = readAll();
        List<List<String>> updatedRecords = new ArrayList<>();

        for (List<String> record : allRecords) {
            if (record.size() > fieldIndex && !record.get(fieldIndex).equals(matchValue)) {
                updatedRecords.add(record);
            }
        }

        writeAll(updatedRecords);
    }

    // Update records matching a condition
    public void updateIf(int fieldIndex, String matchValue, List<String> newRecord) {
        List<List<String>> allRecords = readAll();
        List<List<String>> updatedRecords = new ArrayList<>();

        for (List<String> record : allRecords) {
            if (record.size() > fieldIndex && record.get(fieldIndex).equals(matchValue)) {
                updatedRecords.add(newRecord);
            } else {
                updatedRecords.add(record);
            }
        }

        writeAll(updatedRecords);
    }

    // Helper to overwrite the entire file
    private void writeAll(List<List<String>> records) {
        try (FileWriter fw = new FileWriter(filePath, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (List<String> record : records) {
                out.println(String.join(delimiter, record));
            }

        } catch (IOException e) {
            System.out.println("Could not write to db, error: " + e);
        }
    }
}
