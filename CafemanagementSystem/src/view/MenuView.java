package view;

import controller.MenuController;
import model.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class MenuView extends JFrame {
    public  int itemId=0;
    private JTextField nameField;
    private JTextField priceField;
    private JTextArea descriptionArea;
    private JTable table;
    private JButton addButton;
    private JButton loadButton;

    public MenuView() {
        setTitle("Manage Menu");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        add(panel, BorderLayout.NORTH);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea();
        panel.add(new JScrollPane(descriptionArea));

        addButton = new JButton("ADD");
        panel.add(addButton);

        loadButton=new JButton("Update");
        panel.add(loadButton);


        // Custom table model to include buttons
        MenuTableModel tableModel = new MenuTableModel();
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.getColumn("Load").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Load").setCellEditor(new ButtonEditor(new JCheckBox(), tableModel, this));
        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), tableModel, this));
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getPriceField() {
        return priceField;
    }

    public JTextArea getDescriptionArea() {
        return descriptionArea;
    }

    public JTable getTable() {
        return table;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getLoadButton(){return loadButton;}




}

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    public static int itemId=0;
    protected JButton button;
    private String label;
    private boolean isPushed;
    private MenuTableModel model;
    private MenuView view;
    private JTable table;

    public ButtonEditor(JCheckBox checkBox, MenuTableModel model, MenuView view) {
        super(checkBox);
        this.model = model;
        this.view = view;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;

        button.addActionListener(e -> {
            if (isPushed) {
                if (column == 4) {
                    try {
                        this.itemId=handleLoadAction(row);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (column == 5) {
                    try {
                        handleDeleteAction(row);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        return button;
    }
    private int handleLoadAction(int row) throws SQLException{
        int itemId = (int) model.getValueAt(row, 0);
        String name = (String) model.getValueAt(row, 1);
        double price = (double) model.getValueAt(row, 2);
        String description = (String) model.getValueAt(row, 3);

        view.getNameField().setText(name);
        view.getPriceField().setText(String.valueOf(price));
        view.getDescriptionArea().setText(description);

        return itemId;



    }



    private void handleDeleteAction(int row) throws SQLException {
        int itemId = (int) model.getValueAt(row, 0);
        int result = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            MenuController.deleteMenuItem(itemId);
            model.loadMenuItems();
        }
    }

    @Override
    public Object getCellEditorValue() {
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

