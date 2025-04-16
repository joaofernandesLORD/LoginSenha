import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SubTelaSystem extends JFrame {
    private final GerarTicket geradorTicket;

    public SubTelaSystem() {
        this.geradorTicket = new GerarTicket();

        setTitle("Menu Administrativo");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        try {
            ImageIcon icon = new ImageIcon("Resource_Image/icon.png");
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Erro ao carregar ícone: " + e.getMessage());
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panel.setBackground(CorFundo.getCorPersonalizada(7, 20, 41));

        JButton botaoCadastrarUsuario = criarBotaoPersonalizado("Cadastrar Usuário");
        JButton botaoListaUsuarios = criarBotaoPersonalizado("Lista de Usuários");
        JButton botaoRemoverUsuario = criarBotaoPersonalizado("Remover Usuário");
        JButton botaoModificarUsuario = criarBotaoPersonalizado("Modificar Usuário");
        JButton botaoTelaSystem = criarBotaoPersonalizado("Tela Principal");
        JButton botaoSair = criarBotaoPersonalizado("Sair");

        botaoCadastrarUsuario.addActionListener(this::cadastrarUsuario);
        botaoListaUsuarios.addActionListener(this::exibirListaUsuarios);
        botaoRemoverUsuario.addActionListener(this::removerUsuario);
        botaoModificarUsuario.addActionListener(this::modificarUsuario);
        botaoTelaSystem.addActionListener(e -> voltarParaTelaPrincipal());
        botaoSair.addActionListener(e -> sairDoSistema());

        panel.add(botaoCadastrarUsuario);
        panel.add(botaoListaUsuarios);
        panel.add(botaoRemoverUsuario);
        panel.add(botaoModificarUsuario);
        panel.add(botaoTelaSystem);
        panel.add(botaoSair);

        add(panel);
    }
    private JButton criarBotaoPersonalizado(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(70, 70, 70));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Efeito hover moderno
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(90, 90, 90));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(70, 70, 70));
            }
        });

        return botao;
    }

    private void cadastrarUsuario(ActionEvent e) {
        JLabel statusLabel = new JLabel();
        statusLabel.setForeground(Color.WHITE);

        String[] novoUsuario = LoginController.handleCadastro(statusLabel);

        if (statusLabel.getText() != null && !statusLabel.getText().isEmpty()) {
            int messageType = statusLabel.getForeground() == Color.GREEN ? JOptionPane.INFORMATION_MESSAGE
                    : JOptionPane.ERROR_MESSAGE;

            JOptionPane.showMessageDialog(
                    this,
                    statusLabel.getText(),
                    "Status do Cadastro",
                    messageType);
        }
    }

    private void exibirListaUsuarios(ActionEvent e) {
        try (BufferedReader br = new BufferedReader(new FileReader("credenciais.csv"))) {
            StringBuilder listaUsuarios = new StringBuilder();
            boolean isHeader = true;
            String linha;

            while ((linha = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] partes = linha.split(",");
                if (partes.length == 3) {
                    listaUsuarios.append("   [Login]   | ").append(partes[0].trim()).append("\n");
                    listaUsuarios.append("   [Senha]   | ").append(partes[1].trim()).append("\n");
                    listaUsuarios.append("[Credencial] | ").append(partes[2].trim()).append("\n\n");
                }
            }

            if (listaUsuarios.length() > 0) {
                exibirListaEmDialogo(listaUsuarios.toString());
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Nenhum usuário cadastrado.",
                        "Lista de Usuários",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao ler o arquivo de credenciais.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void exibirListaEmDialogo(String conteudo) {
        JTextArea textArea = new JTextArea(conteudo);
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 300));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Lista de Usuários Cadastrados",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void removerUsuario(ActionEvent e) {
        String login = JOptionPane.showInputDialog(
                this,
                "Digite o login do usuário a ser removido:",
                "Remover Usuário",
                JOptionPane.QUESTION_MESSAGE);

        if (login == null || login.trim().isEmpty()) {
            mostrarMensagem("Operação cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            List<String> linhas = new ArrayList<>();
            boolean usuarioRemovido = false;
            String tipoRemovido = "";

            try (BufferedReader br = new BufferedReader(new FileReader("credenciais.csv"))) {
                String linha;
                boolean isHeader = true;

                while ((linha = br.readLine()) != null) {
                    if (isHeader) {
                        linhas.add(linha);
                        isHeader = false;
                        continue;
                    }

                    String[] partes = linha.split(",");
                    if (partes.length == 3 && partes[0].trim().equalsIgnoreCase(login.trim())) {
                        usuarioRemovido = true;
                        tipoRemovido = partes[2].trim();
                    } else {
                        linhas.add(linha);
                    }
                }
            }

            if (usuarioRemovido) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("credenciais.csv"))) {
                    for (String linha : linhas) {
                        bw.write(linha);
                        bw.newLine();
                    }
                }

                this.geradorTicket.gerarTicketRemocaoUsuario(login, tipoRemovido);

                mostrarMensagem(
                        "Usuário removido com sucesso!\n\nLogin: " + login + "\nTipo: " + tipoRemovido,
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                mostrarMensagem("Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            mostrarMensagem("Erro ao acessar o arquivo de usuários.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void modificarUsuario(ActionEvent e) {
        String login = JOptionPane.showInputDialog(
                this,
                "Digite o login do usuário a ser modificado:",
                "Modificar Usuário",
                JOptionPane.QUESTION_MESSAGE);

        if (login == null || login.trim().isEmpty()) {
            mostrarMensagem("Operação cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            List<String> linhas = new ArrayList<>();
            String[] usuario = null;
            boolean usuarioEncontrado = false;

            try (BufferedReader br = new BufferedReader(new FileReader("credenciais.csv"))) {
                String linha;
                boolean isHeader = true;

                while ((linha = br.readLine()) != null) {
                    if (isHeader) {
                        linhas.add(linha);
                        isHeader = false;
                        continue;
                    }

                    String[] partes = linha.split(",");
                    if (partes.length == 3) {
                        if (partes[0].trim().equalsIgnoreCase(login.trim())) {
                            usuarioEncontrado = true;
                            usuario = new String[] { partes[0].trim(), partes[1].trim(), partes[2].trim() };
                        } else {
                            linhas.add(linha);
                        }
                    }
                }
            }

            if (!usuarioEncontrado || usuario == null) {
                mostrarMensagem("Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] opcoes = { "Modificar Login", "Modificar Senha", "Modificar Tipo" };
            int escolha = JOptionPane.showOptionDialog(
                    this,
                    "Selecione o que deseja modificar no usuário " + login,
                    "Opções de Modificação",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]);

            if (escolha == JOptionPane.CLOSED_OPTION) {
                mostrarMensagem("Operação cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            switch (escolha) {
                case 0: // Modificar Login
                    String novoLogin = JOptionPane.showInputDialog(
                            this,
                            "Digite o novo login:",
                            "Modificar Login",
                            JOptionPane.QUESTION_MESSAGE);

                    if (novoLogin == null || novoLogin.trim().isEmpty()) {
                        mostrarMensagem("Operação cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    usuario[0] = novoLogin.trim();
                    this.geradorTicket.gerarTicketModificacaoUsuario(usuario);
                    break;

                case 1: // Modificar Senha
                    String novaSenha = JOptionPane.showInputDialog(
                            this,
                            "Digite a nova senha:",
                            "Modificar Senha",
                            JOptionPane.QUESTION_MESSAGE);

                    if (novaSenha == null || novaSenha.trim().isEmpty()) {
                        mostrarMensagem("Operação cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    usuario[1] = novaSenha.trim();
                    this.geradorTicket.gerarTicketModificacaoSenha(usuario);
                    break;

                case 2: // Modificar Tipo
                    JComboBox<String> comboBox = new JComboBox<>(
                            new String[] { "ADM", "ENG", "SMT", "BMS", "FA", "LA", "Produção" });
                    comboBox.setSelectedItem(usuario[2]);

                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Selecione o novo tipo:"));
                    panel.add(comboBox);

                    int opcao = JOptionPane.showConfirmDialog(
                            this,
                            panel,
                            "Modificar Tipo de Usuário",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);

                    if (opcao != JOptionPane.OK_OPTION) {
                        mostrarMensagem("Operação cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    usuario[2] = (String) comboBox.getSelectedItem();
                    this.geradorTicket.gerarTicketModificacaoCredencial(usuario);
                    break;
            }

            linhas.add(String.join(",", usuario));
            salvarArquivoCredenciais(linhas);

            mostrarMensagem(
                    "Usuário modificado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            mostrarMensagem("Erro ao acessar o arquivo de usuários.", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void voltarParaTelaPrincipal() {
        dispose();
        SwingUtilities.invokeLater(() -> new TelaSystem("Administrador", "ADM"));
    }

    private void sairDoSistema() {
        dispose();
        SwingUtilities.invokeLater(() -> new LoginSystem().setVisible(true));
    }

    private void salvarArquivoCredenciais(List<String> linhas) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("credenciais.csv"))) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        }
    }

    private void mostrarMensagem(String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                titulo,
                tipo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SubTelaSystem subTela = new SubTelaSystem();
            subTela.setVisible(true);
        });
    }
}