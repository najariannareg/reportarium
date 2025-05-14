package org.reportarium.generator;

import org.reportarium.io.DialogReader;
import org.reportarium.io.OpenCSV;
import org.reportarium.io.OpenPDF;
import org.reportarium.model.Item;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReportGenerator {

    public static final Map<String, String> FORMS = createFormsMap();

    private static Map<String, String> createFormsMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Հավելված 5", "Form-5.csv");
        map.put("Հավելված 6", "Form-6.csv");
        map.put("Հավելված 6.1", "Form-6.1.csv");
        return Collections.unmodifiableMap(map);
    }

    public String generate() {
        DialogReader dialog = new DialogReader();
        Map<String, Set<String>> wantedItems = dialog.readInput();

        OpenCSV reader = new OpenCSV();
        OpenPDF writer = new OpenPDF();

        Map<String, List<Item>> items = reader.read(wantedItems);
        String path = writer.write(items);

        return "Պարոն Անդրանիկ Հովակիմյան ջան, \n" +
                "ձեր հաշվետվությունը պատրաստ է։ \n\n" +
                "Այն կարող եք գտնել հետևյալ հասցեով՝ \n\n" +
                path + " \n\n" +
                "Սիրով, \n" +
                "Նարեկ Նաճարեանի և Ալինա Հովակիմյանի կողմից ";
    }

}
