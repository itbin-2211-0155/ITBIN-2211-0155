package controller;

import model.DatabaseConnection;
import view.HomeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;

        this.view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticate();
            }
        });
    }

    private void authenticate() {
        String username = view.getUsernameField().getText();
        String password = new String(view.getPasswordField().getPassword());

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                view.dispose();
                new HomeController(new HomeView());
            } else {
                JOptionPane.showMessageDialog(view, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
