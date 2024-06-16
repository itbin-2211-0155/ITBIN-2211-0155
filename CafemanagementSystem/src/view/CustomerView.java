package view;

import javax.swing.*;
import java.awt.*;

public class CustomerView extends JFrame {
    private JTextField nameField;
    private JTextField contactField;
    private JTable table;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public CustomerView() {
        setTitle("Manage Customers");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        add(panel, BorderLayout.NORTH);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Contact:"));
        contactField = new JTextField();
        panel.add(contactField);

        addButton = new JButton("Add");
        panel.add(addButton);

        updateButton = new JButton("Update");
        panel.add(updateButton);

        deleteButton = new JButton("Delete");
        panel.add(deleteButton);

        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getContactField() {
        return contactField;
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
