import javax.swing.*;
import java.awt.*;

public class PasswordUtils {

    public static boolean verificarForcaSenha(String senha) {
        if (senha.length() < 8) {
            return false;
        }
        boolean temMaiuscula = false;
        boolean temNumero = false;
        for (char c : senha.toCharArray()) {
            if (Character.isUpperCase(c)) {
                temMaiuscula = true;
            }
            if (Character.isDigit(c)) {
                temNumero = true;
            }
        }
        return temMaiuscula && temNumero;
    }

    public static String showPasswordDialog(String message) {
        JPanel panel = new JPanel(new BorderLayout());
        JPasswordField passwordField = new JPasswordField(20);

        panel.add(new JLabel(message), BorderLayout.NORTH);
        panel.add(passwordField, BorderLayout.CENTER);

        int option = JOptionPane.showOptionDialog(
                null,
                panel,
                "Senha",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);

        if (option == JOptionPane.OK_OPTION) {
            return new String(passwordField.getPassword());
        }
        return null;
    }
}