package bll;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dal.CsvDatabaseLayer;
import dto.DictionaryEntry;

public class CsvBusinessLayer {
    private CsvDatabaseLayer databaseLayer;

    public CsvBusinessLayer(CsvDatabaseLayer databaseLayer) {
        this.databaseLayer = databaseLayer;
    }

    public void processCsvFile(String filePath) throws Exception {
        List<DictionaryEntry> entries = new ArrayList<>();

        // Read the CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 1) {
                    String word = values[0]; // First value is the word
                    Map<String, String> translations = new HashMap<>();

                    // Process key-value pairs for translations
                    for (int i = 1; i < values.length; i++) {
                        String[] keyValue = values[i].split("=");
                        if (keyValue.length == 2) {
                            String language = keyValue[0].trim();
                            String translation = keyValue[1].trim();
                            translations.put(language, translation);
                        } else {
                            throw new Exception("Invalid key-value format in CSV.");
                        }
                    }

                    DictionaryEntry entry = new DictionaryEntry(word, translations);
                    entries.add(entry);
                } else {
                    throw new Exception("CSV file format is invalid.");
                }
            }
        }

        // Save the data to the database layer
        databaseLayer.saveEntries(entries);
    }
}

