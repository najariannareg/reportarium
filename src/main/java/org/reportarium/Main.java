package org.reportarium;

import org.reportarium.io.DialogReader;
import org.reportarium.io.OpenCSV;
import org.reportarium.io.OpenPDF;

import java.util.Map;
import java.util.Set;

public class Main {

    public static final String REPORT = "Report.pdf";

    public static void main(String[] args) {
        DialogReader dialog = new DialogReader();
        Set<String> wantedItems = dialog.read();

        OpenCSV reader = new OpenCSV();
        Map<String, Map<String, String>> items = reader.read(wantedItems);

        OpenPDF writer = new OpenPDF();
        writer.write(REPORT, items);
    }

}