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

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static org.reportarium.generator.ReportGenerator.FORMS;

public class DialogReader {

    public Map<String, Set<String>> readInput() {
        Map<String, Set<String>> map = new LinkedHashMap<>();

        boolean addForm = true;
        while (addForm) {
            String form = selectForm();
            Set<String> items = selectItems(form);

            if (form != null && CollectionUtils.isNotEmpty(items)) {
                map.put(form, items);
            }
            int response = JOptionPane.showConfirmDialog(
                    null,
                    "Ուզում ե՞ք ևս մի հավելված ավելացնել։ ",
                    "Շարունակել",
                    JOptionPane.YES_NO_OPTION
            );
            addForm = (response == JOptionPane.YES_OPTION);
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
                QUESTION_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            return (String) comboBox.getSelectedItem();
        } else {
            throw new CancellationException("Հավելվածը դատարկ է։ Հաշվետվության ստեղծումը չեղարկվեց։ ");
        }
    }

    public Set<String> selectItems(String form) {
        Set<String> wantedItems = new LinkedHashSet<>();

        boolean isValidInput = false;
        while (!isValidInput) {
            String message = "Մուտքագրեք կետերն ու ենթակետերը (Օրինակ՝ 1, 2.1, 3) ";
            String title = form + "-ի տվյալներ";
            String input = JOptionPane.showInputDialog(null, message, title, QUESTION_MESSAGE);
            if (input == null) {
                throw new CancellationException("Կետերն ու ենթակետերը դատարկ են։ Հաշվետվության ստեղծումը չեղարկվեց։ ");
            }
            String[] inputItems = input.split(",");
            for (int i = 0; i < inputItems.length; i++) {
                inputItems[i] = inputItems[i].trim();
            }
            isValidInput = validateInput(inputItems);

            if (isValidInput) {
                wantedItems.addAll(Arrays.asList(inputItems));
            } else {
                JOptionPane.showMessageDialog(null, "Մուտքագրված տվյալները սխալ են։ ");
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
