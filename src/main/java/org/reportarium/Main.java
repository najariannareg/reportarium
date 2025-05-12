package org.reportarium;

import org.reportarium.exception.CancellationException;
import org.reportarium.generator.ReportGenerator;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            ReportGenerator generator = new ReportGenerator();
            generator.generate();
            JOptionPane.showMessageDialog(null, "Հաշվետվությունը ստեղծվել է։");
        } catch (CancellationException cancellationException) {
            JOptionPane.showMessageDialog(null, cancellationException.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Հաշվետվությունը ստեղծելիս սխալ է տեղի ունեցել։ " + e.getMessage());
        }
    }

}