package org.reportarium;

import org.reportarium.io.ItemScanner;
import org.reportarium.io.OpenCSV;
import org.reportarium.io.OpenPDF;

import java.util.Map;
import java.util.Set;

public class Main {

    public static final String REPORT = "Report.pdf";

    public static void main(String[] args) {
        ItemScanner scanner = new ItemScanner();
        Set<String> wantedItems = scanner.scan();

        OpenCSV reader = new OpenCSV();
        Map<String, String> items = reader.read(wantedItems);

        OpenPDF writer = new OpenPDF();
        writer.write(REPORT, items);
    }

}