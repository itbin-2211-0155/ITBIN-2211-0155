package view;

import model.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID", "Name", "Price", "Description", "Load", "Delete"};
    private Object[][] data = {};

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 4 || col == 5;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public void setData(Object[][] data) {
        this.data = data;
        fireTableDataChanged();
    }

    public void loadMenuItems() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM menu";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            Object[][] data = new Object[10][6]; // Adjust the size according to your needs
            int index = 0;

            while (resultSet.next()) {
                int id = resultSet.getInt("item_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                data[index++] = new Object[]{id, name, price, description, "Load", "Delete"};
            }

            setData(data);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading menu items: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void performAction(int row, int column) {
        // Custom method to handle update and delete actions
        fireTableCellUpdated(row, column);
    }
}
