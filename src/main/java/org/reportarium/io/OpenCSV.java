package org.reportarium.io;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class OpenCSV {

    public static final String FORM = "Form-5.csv";
    public static final String NN = "NN";
    public static final String DESCRIPTION = "Description";
    public static final String JUSTIFICATION = "Justification";

    public Map<String, Map<String, String>> read(Set<String> wantedIds) {
        Map<String, Map<String, String>> result = new LinkedHashMap<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FORM)) {
            if (inputStream == null) {
                System.err.println("Form not found in resources");
                return result;
            }
            try (CSVReaderHeaderAware csvReader = new CSVReaderHeaderAware(new InputStreamReader(inputStream))) {
                Map<String, String> row;
                while ((row = csvReader.readMap()) != null) {
                    String item = row.get(NN);
                    if (wantedIds.contains(item)) {
                        result.put(item, Map.of(
                                DESCRIPTION, row.get(DESCRIPTION),
                                JUSTIFICATION, row.get(JUSTIFICATION)));
                    }
                }
            } catch (CsvValidationException e) {
                System.err.println("Error reading CSV: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
            return result;
        }
        return result;
    }

}
