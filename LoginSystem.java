import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginSystem extends JFrame {

    private static final String FILE_PATH = "credenciais.csv";

    public LoginSystem() {
        setTitle("E-MOD");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("Resource_Image/icon.png");
        setIconImage(icon.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(CorFundo.getCorPersonalizada(7, 20, 41));

        ImageIcon footerImage = new ImageIcon("Resource_Image/logo.png");
        Image image = footerImage.getImage();

        int originalWidth = footerImage.getIconWidth();
        int originalHeight = footerImage.getIconHeight();

        double aspectRatio = (double) originalWidth / originalHeight;

        int labelHeight = 50;
        int labelWidth = (int) (labelHeight * aspectRatio);

        Image resizedImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel footerLabel = new JLabel(resizedIcon);
        footerLabel.setBounds((300 - labelWidth) / 2, 220, labelWidth, labelHeight);
        panel.add(footerLabel);

        add(panel);
        placeComponents(panel, this);
    }

    private static void placeComponents(JPanel panel, JFrame loginFrame) {
        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setBounds(40, 10, 135, 25);
        userLabel.setForeground(Color.WHITE);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(90, 10, 135, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(47, 50, 135, 25);
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(90, 50, 135, 25);
        panel.add(passwordText);

        JButton showPasswordButton = new JButton("👁");
        showPasswordButton.setBounds(230, 50, 30, 25);
        showPasswordButton.setMargin(new Insets(0, 0, 0, 0));
        showPasswordButton.setFocusPainted(false);
        panel.add(showPasswordButton);

        showPasswordButton.addActionListener(e -> {
            if (passwordText.getEchoChar() == '\u2022') {
                passwordText.setEchoChar((char) 0);
                showPasswordButton.setText("👁");
            } else {
                passwordText.setEchoChar('\u2022');
                showPasswordButton.setText("👁");
            }
        });

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 90, 100, 30);
        loginButton.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(loginButton);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(100, 125, 100, 30);
        cadastrarButton.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(cadastrarButton);

        JButton resetPasswordButton = new JButton("Resetar Senha");
        resetPasswordButton.setBounds(50, 160, 200, 30);
        resetPasswordButton.setBorderPainted(false);
        resetPasswordButton.setContentAreaFilled(false);
        resetPasswordButton.setFocusPainted(false);
        resetPasswordButton.setForeground(Color.WHITE);
        resetPasswordButton.setFont(new Font("Arial", Font.BOLD, 10));
        panel.add(resetPasswordButton);

        JLabel statusLabel = new JLabel("");
        statusLabel.setBounds(0, 190, 300, 30);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(statusLabel);

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("");
            }
        });
        timer.setRepeats(false);

        // Listeners para os botões
        loginButton.addActionListener(e -> {
            LoginController.handleLogin(userText, passwordText, statusLabel, loginFrame);
            timer.restart();
        });

        cadastrarButton.addActionListener(e -> {
            LoginController.handleCadastro(statusLabel);
            timer.restart();
        });

        resetPasswordButton.addActionListener(e -> {
            LoginController.handleResetSenha(statusLabel);
            timer.restart();
        });

        // Listener para a tecla Enter
        KeyListener enterKeyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        userText.addKeyListener(enterKeyListener);
        passwordText.addKeyListener(enterKeyListener);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginSystem loginSystem = new LoginSystem();
            loginSystem.setVisible(true);
        });
    }
}