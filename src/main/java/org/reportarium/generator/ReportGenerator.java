package org.reportarium.generator;

import org.reportarium.io.DialogReader;
import org.reportarium.io.OpenCSV;
import org.reportarium.io.OpenPDF;
import org.reportarium.model.Item;

import java.util.Map;
import java.util.Set;

public class ReportGenerator {

    public static final String REPORT = "Report.pdf";

    public static final Map<String, String> FORMS = Map.of(
            "Հավելված 5", "Form-5.csv",
            "Հավելված 6", "Form-6.csv",
            "Հավելված 6.1", "Form-6-1.csv"
    );

    public void generate() {
        DialogReader dialog = new DialogReader();
        Map<String, Set<String>> wantedItems = dialog.readInput();

        OpenCSV reader = new OpenCSV();
        OpenPDF writer = new OpenPDF();

        for (Map.Entry<String, Set<String>> entry : wantedItems.entrySet()) {
            Map<String, Item> items = reader.read(FORMS.get(entry.getKey()), entry.getValue());
            writer.write(REPORT, items);
        }

    }

}
