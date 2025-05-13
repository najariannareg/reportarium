package org.reportarium;

import org.reportarium.exception.CancellationException;
import org.reportarium.generator.ReportGenerator;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            ReportGenerator generator = new ReportGenerator();
            String message = generator.generate();
            JOptionPane.showMessageDialog(null, message);
        } catch (CancellationException cancellationException) {
            JOptionPane.showMessageDialog(null, cancellationException.getMessage());
        } catch (Exception e) {
            String errorMessage = "Հաշվետվությունը ստեղծելիս սխալ է տեղի ունեցել։ ";
            JOptionPane.showMessageDialog(null, errorMessage + e.getMessage());
        }
    }

}