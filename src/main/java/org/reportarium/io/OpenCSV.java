package org.reportarium.io;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import org.reportarium.model.Item;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class OpenCSV {

    public static final String NN = "NN";
    public static final String DESCRIPTION = "Description";
    public static final String JUSTIFICATION = "Justification";

    public Map<String, Item> read(String form, Set<String> wantedIds) {
        Map<String, Item> result = new LinkedHashMap<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(form)) {
            if (inputStream == null) {
                System.err.println("Form not found in resources");
                return result;
            }
            try (CSVReaderHeaderAware csvReader = new CSVReaderHeaderAware(new InputStreamReader(inputStream))) {
                Map<String, String> row;
                while ((row = csvReader.readMap()) != null) {
                    String number = row.get(NN);
                    if (wantedIds.contains(number)) {
                        result.put(number, new Item(number, row.get(DESCRIPTION),row.get(JUSTIFICATION)));
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
