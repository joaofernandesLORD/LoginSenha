import javax.swing.*;
import java.awt.*;

public class TelaSystem extends JFrame {

    private static final String FILE_PATH = "credenciais.csv";

    public TelaSystem(String nomeUsuario, String nivelCredencial) {
        // Configurações da janela
        setTitle("Nível de credencial [" + nivelCredencial + "]");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon("Resource_Image/icon.png").getImage());

        // Painel principal com BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        Color corFundoPrincipaColor = CorFundo.getCorPersonalizada(7, 20, 41);
        mainPanel.setBackground(corFundoPrincipaColor);
        add(mainPanel);

        // Painel esquerdo (botões e textos)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null); // Layout nulo para posicionamento manual
        leftPanel.setBackground(corFundoPrincipaColor);

        // Adiciona o painel esquerdo ao centro do painel principal
        mainPanel.add(leftPanel, BorderLayout.CENTER);

        // Adiciona o botão de logout no canto inferior esquerdo
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose(); // Fecha a janela atual (TelaSystem)
            // Reabre a tela de login (LoginSystem)
            SwingUtilities.invokeLater(() -> {
                new LoginSystem().setVisible(true);
            });
        });

        // Painel para o botão de logout (para adicionar margens)
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Alinha o botão à esquerda
        logoutPanel.setBackground(corFundoPrincipaColor); // Mesma cor do painel principal
        logoutPanel.add(logoutButton);

        // Adiciona o painel do botão ao sul do painel principal
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        // Adiciona os componentes ao painel esquerdo
        placeComponents(leftPanel);

        // Torna a janela visível
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        // Caminho da imagem do botão
        String imagePath = "Resource_Image/pasta_inicio.png";

        // Carrega e redimensiona a imagem
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Posicionamento inicial dos botões
        int x = 25; // Posição X inicial
        int y = 5; // Posição Y inicial
        int buttonWidth = 100; // Largura do botão
        int buttonHeight = 100; // Altura do botão
        int horizontalSpacing = 20; // Espaçamento horizontal entre botões
        int verticalSpacing = 20; // Espaçamento vertical entre botões

        // Textos individuais para cada botão
        String[] textos = { "Modelo A066", "Modelo A56", "Modelo A15", "Modelo A25" };

        // Cria e configura os botões com textos abaixo
        for (int i = 0; i < textos.length; i++) {
            // Cria o botão
            JButton button = createButton(scaledIcon, x, y, buttonWidth, buttonHeight);
            panel.add(button);

            // Cria o JLabel com o texto correspondente
            JLabel label = createLabel(textos[i], x, y + buttonHeight - 10, buttonWidth, 20);
            panel.add(label);

            // Atualiza a posição X para o próximo botão
            x += buttonWidth + horizontalSpacing;

            // Se atingir o limite da largura da janela, move para a próxima linha
            if (x + buttonWidth > 800) {
                x = 50; // Reinicia a posição X
                y += buttonHeight + verticalSpacing + 20; // Move para a próxima linha (20 é a altura do JLabel)
            }
        }
    }

    // Método para criar e configurar um botão
    private JButton createButton(ImageIcon icon, int x, int y, int width, int height) {
        JButton button = new JButton(icon);
        button.setBounds(x, y, width, height);
        button.setBorderPainted(false); // Remove a borda
        button.setContentAreaFilled(false); // Remove o fundo
        button.setFocusPainted(false); // Remove o foco ao clicar
        button.setOpaque(false); // Torna o botão transparente
        return button;
    }

    // Método para criar e configurar um JLabel
    private JLabel createLabel(String texto, int x, int y, int width, int height) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER); // Texto centralizado
        label.setBounds(x, y, width, height);
        label.setForeground(Color.WHITE); // Cor do texto
        label.setFont(new Font("Arial", Font.PLAIN, 12)); // Fonte do texto
        return label;
    }

    public static void main(String[] args) {
        // Exibe a tela de login
        SwingUtilities.invokeLater(() -> {
            TelaSystem telaSystem = new TelaSystem("Usuario", "ADM");
            telaSystem.setVisible(true);
        });
    }

}