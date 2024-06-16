package controller;

import model.DatabaseConnection;
import model.Order;
import model.DaoUtils;
import view.OrderView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class OrderController {
    private OrderView view;

    public OrderController(OrderView view) {
        this.view = view;
        this.view.setVisible(true);

        loadOrders();

        this.view.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });

        this.view.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOrder();
            }
        });

        this.view.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
    }

    private void loadOrders() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM orders";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            view.getTable().setModel(DaoUtils.resultSetToTableModel(resultSet));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addOrder() {
        int customerId = Integer.parseInt(view.getCustomerIdField().getText());
        double totalAmount = Double.parseDouble(view.getTotalAmountField().getText());

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO orders (customer_id, total_amount, order_date) VALUES (?, ?, NOW())";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            statement.setDouble(2, totalAmount);

            statement.executeUpdate();
            loadOrders();
            JOptionPane.showMessageDialog(view, "Order added successfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateOrder() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Select an order to update");
            return;
        }

        int orderId = (int) view.getTable().getValueAt(selectedRow, 0);
        int customerId = Integer.parseInt(view.getCustomerIdField().getText());
        double totalAmount = Double.parseDouble(view.getTotalAmountField().getText());

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE orders SET customer_id = ?, total_amount = ? WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerId);
            statement.setDouble(2, totalAmount);
            statement.setInt(3, orderId);

            statement.executeUpdate();
            loadOrders();
            JOptionPane.showMessageDialog(view, "Order updated successfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteOrder() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Select an order to delete");
            return;
        }

        int orderId = (int) view.getTable().getValueAt(selectedRow, 0);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM orders WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);

            statement.executeUpdate();
            loadOrders();
            JOptionPane.showMessageDialog(view, "Order deleted successfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

