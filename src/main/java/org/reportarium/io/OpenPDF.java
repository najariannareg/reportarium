package org.reportarium.io;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.reportarium.io.OpenCSV.DESCRIPTION;
import static org.reportarium.io.OpenCSV.JUSTIFICATION;

public class OpenPDF {

    public static final String FONT = "NotoSansArmenian-Regular.ttf";

    public void write(String filename, Map<String, Map<String, String>> items) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FONT)) {
                if (inputStream == null) {
                    System.err.println("Font not found in resources");
                    return;
                }
                BaseFont baseFont = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, false, inputStream.readAllBytes(), null);
                Font font = new Font(baseFont, 12, Font.NORMAL);

                document.add(new Paragraph("Violations Report"));

                for (Map.Entry<String, Map<String, String>> item : items.entrySet()) {
                    Map<String, String> values = item.getValue();
                    String text = item.getKey() + ": " + values.get(DESCRIPTION) + "\n" + values.get(JUSTIFICATION);
                    document.add(new Paragraph(text, font));
                }
            } catch (IOException e) {
                System.err.println("Error accessing font: " + e.getMessage());
                return;
            }

            document.close();
        } catch (IOException e) {
            System.err.println("Error writing PDF: " + e.getMessage());
        }
    }

}
