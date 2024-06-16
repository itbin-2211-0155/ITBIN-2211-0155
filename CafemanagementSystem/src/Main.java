import view.LoginView;
import controller.LoginController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
            new LoginController(loginView);
        });
    }
}
