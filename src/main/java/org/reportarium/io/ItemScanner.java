package org.reportarium.io;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class ItemScanner {

    public Set<String> scan() {
        Set<String> wantedItems = new LinkedHashSet<>();

        boolean isValidInput = false;
        while (!isValidInput) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the item numbers you want to include (e.g., 1, 2.1, 3): ");
            String input = scanner.nextLine();

            String[] inputItems = input.split(",");
            for (int i = 0; i < inputItems.length; i++) {
                inputItems[i] = inputItems[i].trim();
            }
            isValidInput = validateInput(inputItems);

            if (isValidInput) {
                wantedItems.addAll(Arrays.asList(inputItems));
            } else {
                System.out.println("Invalid input.");
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
