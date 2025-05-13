package org.reportarium.io;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.reportarium.model.Item;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class OpenPDF {

    public static final String USER = "user.home";
    public static final String DIRECTORY = "Documents";
    public static final String FOLDER = "Reportarium";
    public static final String REPORT = "Report.pdf";
    public static final String FONT = "NotoSansArmenian-Regular.ttf";

    public void write(Map<String, List<Item>> items) {
        FileOutputStream fileOutputStream = getFileOutputStream();
        if (fileOutputStream == null) {
            return;
        }
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(document, fileOutputStream);
        document.open();

        BaseFont baseFont = getFont();
        if (baseFont == null) {
            return;
        }
        addTitle(baseFont, document);

        for (Map.Entry<String, List<Item>> entry : items.entrySet()) {
            addHeading(entry.getKey(), baseFont, document);
            addItems(entry.getValue(), baseFont, document);
        }
        document.close();
    }

    private FileOutputStream getFileOutputStream() {
        File out = getOutFile();
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(out);
        } catch (IOException e) {
            System.err.println("Error writing PDF: " + e.getMessage());
            return null;
        }
        System.out.println("Writing PDF to: " + out.getAbsolutePath());
        return fileOutputStream;
    }

    private File getOutFile() {
        String userDocs = System.getProperty(USER) + File.separator + DIRECTORY;

        File reportDir = new File(userDocs, FOLDER);
        if (!reportDir.exists()) {
            boolean created = reportDir.mkdirs();
            if (!created) {
                System.err.println("Error creating directory: " + reportDir.getAbsolutePath());
                return new File(userDocs, REPORT);
            }
        }
        return new File(reportDir, REPORT);
    }

    private BaseFont getFont() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FONT)) {
            if (inputStream == null) {
                System.err.println("Font not found in resources");
                return null;
            }
            return BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, false, inputStream.readAllBytes(), null);
        } catch (IOException e) {
            System.err.println("Error accessing font: " + e.getMessage());
            return null;
        }
    }

    private static void addTitle(BaseFont baseFont, Document document) {
        String titleName = "ՀԱՇՎԵՏՎՈՒԹՅՈՒՆ ԽԱԽՏՈՒՄՆԵՐԻ ՄԱՍԻՆ";
        Paragraph title = new Paragraph(titleName, new Font(baseFont, 16, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);
    }

    private static void addHeading(String formName, BaseFont baseFont, Document document) {
        Paragraph heading = new Paragraph(formName, new Font(baseFont, 14, Font.UNDERLINE));
        heading.setAlignment(Element.ALIGN_LEFT);
        heading.setSpacingBefore(20f);
        heading.setSpacingAfter(10f);
        document.add(heading);
    }

    private static void addItems(List<Item> items, BaseFont baseFont, Document document) {
        Font font = new Font(baseFont, 12, Font.NORMAL);

        com.lowagie.text.List pdfList = new com.lowagie.text.List(true, 20f);
        for (Item item : items) {
            String text = item.number() + ": " + item.description() + "\n" + item.justification();

            ListItem listItem = new ListItem(text, font);
            listItem.setSpacingAfter(10f);
            pdfList.add(listItem);
        }
        document.add(pdfList);
    }

}
