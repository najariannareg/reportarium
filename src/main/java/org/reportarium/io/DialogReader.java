package org.reportarium.io;

import org.apache.commons.collections.CollectionUtils;
import org.reportarium.exception.CancellationException;

import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static org.reportarium.generator.ReportGenerator.FORMS;

public class DialogReader {

    public Map<String, Set<String>> readInput() {
        Map<String, Set<String>> map = new LinkedHashMap<>();

        String form = selectForm();
        Set<String> items = selectItems();

        if (form != null && CollectionUtils.isNotEmpty(items)) {
            map.put(form, items);
        }

        return map;
    }

    public String selectForm() {
        String[] choices = FORMS.keySet().toArray(new String[0]);

        JComboBox<String> comboBox = new JComboBox<>(choices);
        comboBox.setSelectedIndex(0);

        int result = JOptionPane.showConfirmDialog(
                null,
                comboBox,
                "Ընտրեք Հավելվածը",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            return (String) comboBox.getSelectedItem();
        } else {
            throw new CancellationException("Հավելվածը դատարկ է։ Հաշվետվության ստեղծումը չեղարկվեց։");
        }
    }

    public Set<String> selectItems() {
        Set<String> wantedItems = new LinkedHashSet<>();

        boolean isValidInput = false;
        while (!isValidInput) {
            String input = JOptionPane.showInputDialog(null, "Մուտքագրեք կետերն ու ենթակետերը (Օրինակ՝ 1, 2.1, 3)");
            if (input == null) {
                throw new CancellationException("Կետերն ու ենթակետերը դատարկ են։ Հաշվետվության ստեղծումը չեղարկվեց։");
            }
            String[] inputItems = input.split(",");
            for (int i = 0; i < inputItems.length; i++) {
                inputItems[i] = inputItems[i].trim();
            }
            isValidInput = validateInput(inputItems);

            if (isValidInput) {
                wantedItems.addAll(Arrays.asList(inputItems));
            } else {
                JOptionPane.showMessageDialog(null, "Մուտքագրված տվյալները սխալ են։");
            }
        }
        return wantedItems;
    }

    private boolean validateInput(String[] inputItems) {
        // Pattern to match integer or decimal-like numbers (e.g., "2.1")
        String regex = "^[0-9]+(\\.[0-9]+)?$";
        Pattern pattern = Pattern.compile(regex);

        for (String item : inputItems) {
            if (!pattern.matcher(item.trim()).matches()) {
                return false;
            }
        }
        return true;
    }

}
