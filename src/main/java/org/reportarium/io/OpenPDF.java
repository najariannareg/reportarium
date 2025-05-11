package org.reportarium.io;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
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
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();

            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FONT)) {
                if (inputStream == null) {
                    System.err.println("Font not found in resources");
                    return;
                }
                BaseFont baseFont = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, false, inputStream.readAllBytes(), null);
                Font font = new Font(baseFont, 12, Font.NORMAL);

                Paragraph title = new Paragraph("ՀԱՇՎԵՏՎՈՒԹՅՈՒՆ ԽԱԽՏՈՒՄՆԵՐԻ ՄԱՍԻՆ", new Font(baseFont, 16, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20f);
                document.add(title);

                Paragraph heading = new Paragraph("ՀԱՎԵԼՎԱԾ 5:", new Font(baseFont, 14, Font.UNDERLINE));
                heading.setAlignment(Element.ALIGN_LEFT);
                heading.setSpacingAfter(20f);
                document.add(heading);

                com.lowagie.text.List pdfList = new com.lowagie.text.List(true, 20f);

                for (Map.Entry<String, Map<String, String>> item : items.entrySet()) {
                    Map<String, String> values = item.getValue();
                    String text = item.getKey() + ": " + values.get(DESCRIPTION) + "\n" + values.get(JUSTIFICATION);

                    ListItem listItem = new ListItem(text, font);
                    listItem.setSpacingAfter(10f);
                    pdfList.add(listItem);
                }

                document.add(pdfList);
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
