package org.reportarium.io;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class OpenPDF {

    public void write(String filename, Map<String, String> items) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            document.add(new Paragraph("Violations Report"));

            for (Map.Entry<String, String> item : items.entrySet()) {
                document.add(new Paragraph(item.getKey() + ": " + item.getValue()));
            }
            document.close();
        } catch (IOException e) {
            System.err.println("Error writing PDF: " + e.getMessage());
        }
    }

}
