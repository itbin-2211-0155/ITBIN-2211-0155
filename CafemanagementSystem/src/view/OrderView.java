package view;

import javax.swing.*;
import java.awt.*;

public class OrderView extends JFrame {
    private JTextField customerIdField;
    private JTextField totalAmountField;
    private JTable table;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public OrderView() {
        setTitle("Manage Orders");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        add(panel, BorderLayout.NORTH);

        panel.add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        panel.add(customerIdField);

        panel.add(new JLabel("Total Amount:"));
        totalAmountField = new JTextField();
        panel.add(totalAmountField);

        addButton = new JButton("Add");
        panel.add(addButton);

        updateButton = new JButton("Update");
        panel.add(updateButton);

        deleteButton = new JButton("Delete");
        panel.add(deleteButton);

        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public JTextField getCustomerIdField() {
        return customerIdField;
    }

    public JTextField getTotalAmountField() {
        return totalAmountField;
    }

    public JTable getTable() {
        return table;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
}
