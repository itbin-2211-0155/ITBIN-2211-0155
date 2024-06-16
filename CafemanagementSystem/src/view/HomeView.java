package view;

import javax.swing.*;
import java.awt.*;

public class HomeView extends JFrame {
    private JButton menuButton;
    private JButton ordersButton;
    private JButton customersButton;
    private JButton logoutButton;

    public HomeView() {
        setTitle("Home");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        add(panel);

        menuButton = new JButton("Manage Menu");
        panel.add(menuButton);

        ordersButton = new JButton("Manage Orders");
        panel.add(ordersButton);

        customersButton = new JButton("Manage Customers");
        panel.add(customersButton);

        logoutButton = new JButton("Logout");
        panel.add(logoutButton);
    }

    public JButton getMenuButton() {
        return menuButton;
    }

    public JButton getOrdersButton() {
        return ordersButton;
    }

    public JButton getCustomersButton() {
        return customersButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }
}
