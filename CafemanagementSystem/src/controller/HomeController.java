package controller;

import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController {
    private HomeView view;

    public HomeController(HomeView view) {
        this.view = view;
        this.view.setVisible(true);

        this.view.getMenuButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuController(new MenuView());
            }
        });

        this.view.getOrdersButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderController(new OrderView());
            }
        });

        this.view.getCustomersButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerController(new CustomerView());
            }
        });

        this.view.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                new LoginController(new LoginView());
            }
        });
    }
}
