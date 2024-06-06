import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;

@SuppressWarnings("unused")
public class InvoiceManagementSystem extends JFrame {
    private JTextField customerNameField;
    private JTextField invoiceDateField;
    private JTextField totalField;
    private JCheckBox onionCheckBox;
    private JCheckBox cheeseCheckBox;
    private JCheckBox tomatoCheckBox;
    private JCheckBox babyCornCheckBox;
    private JRadioButton panPizzaRadioButton;
    private JRadioButton stuffedRadioButton;
    private JRadioButton regularRadioButton;
    private JButton calculateButton;
    private JButton printButton;

    public InvoiceManagementSystem() {
        createGUI();
    }

    private void createGUI() {
        // Create frame and set title
        JFrame frame = new JFrame("Invoice Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Create customer name field
        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameField = new JTextField(20);
        frame.add(customerNameLabel);
        frame.add(customerNameField);

        // Create invoice date field
        JLabel invoiceDateLabel = new JLabel("Invoice Date:");
        invoiceDateField = new JTextField(20);
        frame.add(invoiceDateLabel);
        frame.add(invoiceDateField);

        // Create total field
        JLabel totalLabel = new JLabel("Total:");
        totalField = new JTextField(20);
        totalField.setEditable(false);
        frame.add(totalLabel);
        frame.add(totalField);

        // Create pizza type panel
        JPanel pizzaPanel = new JPanel();
        pizzaPanel.setLayout(new FlowLayout());
        JLabel pizzaLabel = new JLabel("Pizza Type:");
        panPizzaRadioButton = new JRadioButton("Pan Pizza");
        stuffedRadioButton = new JRadioButton("Stuffed");
        regularRadioButton = new JRadioButton("Regular");
        ButtonGroup pizzaGroup = new ButtonGroup();
        pizzaGroup.add(panPizzaRadioButton);
        pizzaGroup.add(stuffedRadioButton);
        pizzaGroup.add(regularRadioButton);
        pizzaPanel.add(pizzaLabel);
        pizzaPanel.add(panPizzaRadioButton);
        pizzaPanel.add(stuffedRadioButton);
        pizzaPanel.add(regularRadioButton);
        frame.add(pizzaPanel);

        // Create topping panel
        JPanel toppingPanel = new JPanel();
        toppingPanel.setLayout(new FlowLayout());
        JLabel toppingLabel = new JLabel("Toppings:");
        onionCheckBox = new JCheckBox("Onion");
        cheeseCheckBox = new JCheckBox("Cheese");
        tomatoCheckBox = new JCheckBox("Tomato");
        babyCornCheckBox = new JCheckBox("Baby Corn");
        toppingPanel.add(toppingLabel);
        toppingPanel.add(onionCheckBox);
        toppingPanel.add(cheeseCheckBox);
        toppingPanel.add(tomatoCheckBox);
        toppingPanel.add(babyCornCheckBox);
        frame.add(toppingPanel);

        // Create calculate button
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new CalculateButtonListener());
        frame.add(calculateButton);

        // Create print button
        printButton = new JButton("Print");
        printButton.addActionListener(new PrintButtonListener());
        frame.add(printButton);

        // Set frame size and visibility
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private class CalculateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double total = calculateTotal();
            totalField.setText(String.valueOf(total));
        }

        private double calculateTotal() {
            double total = 0;
            if (panPizzaRadioButton.isSelected()) {
                total += 200;
            } else if (stuffedRadioButton.isSelected()) {
                total += 300;
            } else if (regularRadioButton.isSelected()) {
                total += 150;
            }
            if (onionCheckBox.isSelected()) {
                total += 60;
            }
            if (cheeseCheckBox.isSelected()) {
                total += 30;
            }
            if (tomatoCheckBox.isSelected()) {
                total += 40;
            }
            if (babyCornCheckBox.isSelected()) {
                total += 50;
            }
            return total;
        }
    }

    private class PrintButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            doPrint();
        }
    }

    private void doPrint() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new InvoicePrintable());
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class InvoicePrintable implements Printable {
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            String customerName = customerNameField.getText();
            String invoiceDate = invoiceDateField.getText();
            String pizzaType = "";
            if (panPizzaRadioButton.isSelected()) {
                pizzaType = "Pan Pizza";
            } else if (stuffedRadioButton.isSelected()) {
                pizzaType = "Stuffed";
            } else if (regularRadioButton.isSelected()) {
                pizzaType = "Regular";
            }
            String toppings = "";
            if (onionCheckBox.isSelected()) {
                toppings += "Onion, ";
            }
            if (cheeseCheckBox.isSelected()) {
                toppings += "Cheese, ";
            }
            if (tomatoCheckBox.isSelected()) {
                toppings += "Tomato, ";
            }
            if (babyCornCheckBox.isSelected()) {
                toppings += "Baby Corn, ";
            }
            double total = Double.parseDouble(totalField.getText());

            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString("Invoice Management System", 50, 50);
            g2d.drawString("Customer Name: " + customerName, 50, 70);
            g2d.drawString("Invoice Date: " + invoiceDate, 50, 90);
            g2d.drawString("Pizza Type: " + pizzaType, 50, 110);
            g2d.drawString("Toppings: " + toppings, 50, 130);
            g2d.drawString("Total: $" + String.format("%.2f", total), 50, 150);

            return PAGE_EXISTS;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InvoiceManagementSystem());
    }
}

