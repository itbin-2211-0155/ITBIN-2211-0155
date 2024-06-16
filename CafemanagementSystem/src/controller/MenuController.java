package controller;

import model.DatabaseConnection;
import view.MenuView;
import view.MenuTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MenuController {
    private static MenuView view;
    private static MenuTableModel tableModel;

    public MenuController(MenuView view) {
        this.view = view;
        this.view.setVisible(true);

        this.tableModel = (MenuTableModel) view.getTable().getModel();

        loadMenuItems();

        this.view.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddAction();
            }
        });

        this.view.getLoadButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleUpdateAction(28);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


    }

    public void handleUpdateAction(int itemId) throws SQLException {


        int result = JOptionPane.showConfirmDialog(view, "Do you want to update this item?", "Confirm Update", JOptionPane.YES_NO_OPTION);


        if (result == JOptionPane.YES_OPTION) {
            MenuController.updateMenuItem(itemId, view.getNameField().getText(), Double.parseDouble(view.getPriceField().getText()), view.getDescriptionArea().getText());
        }
    }

    private void loadMenuItems() {
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

            tableModel.setData(data);
        } catch (SQLException ex) {
            showErrorDialog("Error loading menu items: " + ex.getMessage());
        }
    }

    private void handleAddAction() {
        String name = view.getNameField().getText();
        String priceText = view.getPriceField().getText();
        String description = view.getDescriptionArea().getText();

        if (!isValidInput(name, priceText)) {
            return;
        }

        double price = Double.parseDouble(priceText);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO menu (name, price, description) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.setString(3, description);

            statement.executeUpdate();
            loadMenuItems();
            showInfoDialog("Menu item added successfully");
        } catch (SQLException ex) {
            showErrorDialog("Error adding menu item: " + ex.getMessage());
        }
    }

    public static void updateMenuItem(int itemId, String name, double price, String description) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE menu SET name = ?, price = ?, description = ? WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.setString(3, description);
            statement.setInt(4, itemId);

            statement.executeUpdate();
            ((MenuTableModel) view.getTable().getModel()).loadMenuItems();
            JOptionPane.showMessageDialog(view, "Menu item updated successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error updating menu item: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void deleteMenuItem(int itemId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM menu WHERE item_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, itemId);

            statement.executeUpdate();
            ((MenuTableModel) view.getTable().getModel()).loadMenuItems();
            JOptionPane.showMessageDialog(view, "Menu item deleted successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error deleting menu item: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidInput(String name, String priceText) {
        if (name.isEmpty() || priceText.isEmpty()) {
            showErrorDialog("Name and Price fields cannot be empty");
            return false;
        }

        try {
            Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showErrorDialog("Price must be a valid number");
            return false;
        }

        return true;
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(view, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
