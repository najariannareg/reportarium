package org.reportarium.io;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.collections.CollectionUtils;
import org.reportarium.model.Item;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.reportarium.generator.ReportGenerator.FORMS;

public class OpenCSV {

    public static final String NN = "NN";
    public static final String DESCRIPTION = "Description";
    public static final String JUSTIFICATION = "Justification";

    public Map<String, List<Item>> read(Map<String, Set<String>> formToWantedIdsMap) {
        Map<String, List<Item>> result = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : formToWantedIdsMap.entrySet()) {
            String form = FORMS.get(entry.getKey());

            List<Item> items = getItems(form, entry.getValue());
            if (CollectionUtils.isNotEmpty(items)) {
                result.put(entry.getKey(), items);
            }
        }
        return result;
    }

    private List<Item> getItems(String form, Set<String> wantedIds) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(form)) {
            if (inputStream == null) {
                System.err.println("Form not found in resources");
                return null;
            }
            return getItems(inputStream, wantedIds);
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
            return null;
        }
    }

    private List<Item> getItems(InputStream inputStream, Set<String> wantedIds) {
        List<Item> result = new ArrayList<>();

        try (CSVReaderHeaderAware csvReader = new CSVReaderHeaderAware(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            Map<String, String> row;
            while ((row = csvReader.readMap()) != null) {
                String number = row.get(NN);
                if (wantedIds.contains(number)) {
                    result.add(new Item(number, row.get(DESCRIPTION),row.get(JUSTIFICATION)));
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
        }
        return result;
    }

}
