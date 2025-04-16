import javax.swing.*;
import java.awt.*;

public class TelaSystem extends JFrame {

    private static final String FILE_PATH = "credenciais.csv";

    public TelaSystem(String nomeUsuario, String nivelCredencial) {
        setTitle("Nível de credencial [" + nivelCredencial + "]");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon("Resource_Image/icon.png").getImage());

        JPanel mainPanel = new JPanel(new BorderLayout());
        Color corFundoPrincipaColor = CorFundo.getCorPersonalizada(7, 20, 41);
        mainPanel.setBackground(corFundoPrincipaColor);
        add(mainPanel);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(corFundoPrincipaColor);

        mainPanel.add(leftPanel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new LoginSystem().setVisible(true);
            });
        });

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoutPanel.setBackground(corFundoPrincipaColor);
        logoutPanel.add(logoutButton);

        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Exibe a tela de login
        SwingUtilities.invokeLater(() -> {
            TelaSystem telaSystem = new TelaSystem("Usuario", "ADM");
            telaSystem.setVisible(true);
        });
    }

}