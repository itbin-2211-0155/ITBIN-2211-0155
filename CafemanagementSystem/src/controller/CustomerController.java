package controller;

import model.DatabaseConnection;
import model.Customer;
import model.DaoUtils;
import view.CustomerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CustomerController {
    private CustomerView view;

    public CustomerController(CustomerView view) {
        this.view = view;
        this.view.setVisible(true);

        loadCustomers();

        this.view.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        this.view.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustomer();
            }
        });

        this.view.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
            }
        });
    }

    private void loadCustomers() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM customers";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            view.getTable().setModel(DaoUtils.resultSetToTableModel(resultSet));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addCustomer() {
        String name = view.getNameField().getText();
        String contact = view.getContactField().getText();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO customers (name, contact) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, contact);

            statement.executeUpdate();
            loadCustomers();
            JOptionPane.showMessageDialog(view, "Customer added successfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateCustomer() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Select a customer to update");
            return;
        }

        int customerId = (int) view.getTable().getValueAt(selectedRow, 0);
        String name = view.getNameField().getText();
        String contact = view.getContactField().getText();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE customers SET name = ?, contact = ? WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, contact);
            statement.setInt(3, customerId);

            statement.executeUpdate();
            loadCustomers();
            JOptionPane.showMessageDialog(view, "Customer updated successfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteCustomer() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Select a customer to delete");
            return;
        }

        int customerId = (int) view.getTable().getValueAt(selectedRow, 0);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM customers WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);

            statement.executeUpdate();
            loadCustomers();
            JOptionPane.showMessageDialog(view, "Customer deleted successfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
