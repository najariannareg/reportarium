package org.reportarium.io;

import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class DialogReader {

    public Set<String> read() {
        Set<String> wantedItems = new LinkedHashSet<>();

        boolean isValidInput = false;
        while (!isValidInput) {

            String input = JOptionPane.showInputDialog(null, "Մուտքագրեք կետերն ու ենթակետերը (Օրինակ՝ 1, 2.1, 3)");

            String[] inputItems = input.split(",");
            for (int i = 0; i < inputItems.length; i++) {
                inputItems[i] = inputItems[i].trim();
            }
            isValidInput = validateInput(inputItems);

            if (isValidInput) {
                wantedItems.addAll(Arrays.asList(inputItems));
                JOptionPane.showMessageDialog(null, "Հաշվետվությունը ստեղծված է։");
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
