import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class LoginController {
    private static final String FILE_PATH = "credenciais.csv";
    private static final GerarTicket geradorTicket = new GerarTicket();

    private static final String[] TIPOS_USUARIO = {
            "ADM", "ENG", "SMT", "BMS", "FA", "LA", "Produção"
    };

    public static void handleLogin(JTextField userText, JPasswordField passwordText,
            JLabel statusLabel, JFrame loginFrame) {
        String login = userText.getText();
        String senha = new String(passwordText.getPassword());

        try {
            Map<String, String[]> credenciais = FileManager.readCredentialsFromCSV(FILE_PATH);
            if (credenciais.containsKey(login)) {
                String[] credencial = credenciais.get(login);
                if (credencial[0].equals(senha)) {
                    statusLabel.setText("Login bem-sucedido!");
                    statusLabel.setForeground(Color.GREEN);

                    loginFrame.dispose(); // Fecha a janela de login primeiro

                    // Debug: Mostra qual tipo de usuário está sendo detectado
                    System.out.println("Tipo de usuário detectado: " + credencial[1]);

                    if ("ADM".equalsIgnoreCase(credencial[1])) {
                        System.out.println("Redirecionando para SubTelaSystem...");
                        SwingUtilities.invokeLater(() -> {
                            SubTelaSystem subTela = new SubTelaSystem();
                            subTela.setVisible(true);
                        });
                    } else {
                        System.out.println("Redirecionando para TelaSystem...");
                        SwingUtilities.invokeLater(() -> {
                            new TelaSystem(login, credencial[1]);
                        });
                    }
                } else {
                    statusLabel.setText("Senha incorreta!");
                    statusLabel.setForeground(Color.RED);
                }
            } else {
                statusLabel.setText("Usuário não encontrado!");
                statusLabel.setForeground(Color.RED);
            }
        } catch (IOException ex) {
            statusLabel.setText("Erro ao ler o arquivo de credenciais.");
            statusLabel.setForeground(Color.RED);
            ex.printStackTrace();
        }
    }

    public static String[] handleCadastro(JLabel statusLabel) {
        // Solicita senha de autenticação para cadastro
        String senhaAutenticacao = PasswordUtils.showPasswordDialog(
                "Digite a senha de autenticação para cadastrar novo usuário:");

        // Verifica senha de autenticação
        if (senhaAutenticacao == null || !senhaAutenticacao.equals("lulu2025")) {
            statusLabel.setText("Senha de autenticação incorreta!");
            statusLabel.setForeground(Color.RED);
            return null;
        }

        // Obtém login único
        String login = obterLoginUnico(statusLabel);
        if (login == null)
            return null;

        // Obtém senha forte
        String senha = obterSenhaForte(statusLabel);
        if (senha == null)
            return null;

        // Seleciona tipo de usuário
        String credencialUsuario = selecionarcredencialUsuario();
        if (credencialUsuario == null) {
            statusLabel.setText("Cadastro cancelado.");
            statusLabel.setForeground(Color.RED);
            return null;
        }

        try {
            // Tenta cadastrar o novo usuário
            FileManager.cadastrarNovoUsuario(login, senha, credencialUsuario);

            // Gera o ticket de cadastro (ocultando a senha por segurança)
            geradorTicket.gerarTicketCadastroUsuario(new String[] { login, senha, credencialUsuario });

            // Atualiza o status
            statusLabel.setText("Usuário " + login + " cadastrado com sucesso!");
            statusLabel.setForeground(Color.GREEN);

            return new String[] { login, senha, credencialUsuario };

        } catch (IOException ex) {
            String errorMsg = "Erro ao salvar credenciais: " + ex.getMessage();
            System.err.println(errorMsg);
            ex.printStackTrace();

            // Mensagem mais amigável para o usuário
            statusLabel.setText("Falha no cadastro. Tente novamente.");
            statusLabel.setForeground(Color.RED);

            return null;
        }
    }

    private static void redirecionarPorcredencialUsuario(String credencialUsuario, JFrame loginFrame) {
        loginFrame.dispose();
        switch (credencialUsuario) {
            case "ADM":
                new SubTelaSystem();
                break;
            case "ENG":
            case "SMT":
            case "BMS":
            case "FA":
            case "LA":
            case "Produção":
                new TelaSystem("Usuario", credencialUsuario);
                break;
            default:
                new LoginSystem().setVisible(true);
        }
    }

    private static String obterLoginUnico(JLabel statusLabel) {
        String login;
        do {
            login = JOptionPane.showInputDialog("Digite o login para o novo usuário:");
            if (login == null) {
                statusLabel.setText("Cadastro cancelado.");
                statusLabel.setForeground(Color.RED);
                return null;
            }

            try {
                Map<String, String[]> credenciais = FileManager.readCredentialsFromCSV(FILE_PATH);
                if (credenciais.containsKey(login)) {
                    String[] credencial = credenciais.get(login);
                    JOptionPane.showMessageDialog(null,
                            "Login já existe!\nLogin: " + login + "\nTipo: " + credencial[1],
                            "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    return login;
                }
            } catch (IOException ex) {
                statusLabel.setText("Erro ao verificar login.");
                statusLabel.setForeground(Color.RED);
                ex.printStackTrace();
                return null;
            }
        } while (true);
    }

    private static String obterSenhaForte(JLabel statusLabel) {
        String senha;
        do {
            senha = PasswordUtils.showPasswordDialog(
                    "Digite a senha (mínimo 8 caracteres, com número, maiúsculas e minúsculas):");

            if (senha == null) {
                statusLabel.setText("Cadastro cancelado.");
                statusLabel.setForeground(Color.RED);
                return null;
            }

            if (PasswordUtils.verificarForcaSenha(senha)) {
                return senha;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Senha fraca! Deve ter:\n- 8+ caracteres\n- Números\n- Maiúsculas\n- Minúsculas",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } while (true);
    }

    private static String selecionarcredencialUsuario() {
        JComboBox<String> comboBox = new JComboBox<>(TIPOS_USUARIO);
        comboBox.setSelectedIndex(0);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Selecione o tipo de usuário:"), BorderLayout.NORTH);
        panel.add(comboBox, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Tipo de Usuário",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        return (option == JOptionPane.OK_OPTION) ? (String) comboBox.getSelectedItem() : null;
    }

    public static void handleResetSenha(JLabel statusLabel) {
        // Autenticação inicial
        String senhaAutenticacao = PasswordUtils.showPasswordDialog(
                "Digite a senha de autenticação para resetar senha:");

        if (senhaAutenticacao == null || !senhaAutenticacao.equals("lulu2025")) {
            statusLabel.setText("Senha incorreta!");
            statusLabel.setForeground(Color.RED);
            return;
        }

        // Obter login válido
        String login = JOptionPane.showInputDialog("Digite o login para resetar senha:");
        if (login == null || login.isEmpty()) {
            statusLabel.setText("Login inválido!");
            statusLabel.setForeground(Color.RED);
            return;
        }

        try {
            Map<String, String[]> credenciais = FileManager.readCredentialsFromCSV(FILE_PATH);
            if (!credenciais.containsKey(login)) {
                statusLabel.setText("Usuário não encontrado!");
                statusLabel.setForeground(Color.RED);
                return;
            }

            // Obter nova senha forte e diferente
            String novaSenha;
            String senhaAtual = credenciais.get(login)[0];
            String credencialUsuario = credenciais.get(login)[1];

            do {
                novaSenha = PasswordUtils.showPasswordDialog(
                        "Digite a nova senha (diferente da atual):");

                if (novaSenha == null) {
                    statusLabel.setText("Operação cancelada.");
                    statusLabel.setForeground(Color.RED);
                    return;
                }

                if (novaSenha.equals(senhaAtual)) {
                    JOptionPane.showMessageDialog(null,
                            "A nova senha deve ser diferente da atual.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                } else if (!PasswordUtils.verificarForcaSenha(novaSenha)) {
                    JOptionPane.showMessageDialog(null,
                            "Senha fraca! Deve ter:\n- 8+ caracteres\n- Números\n- Maiúsculas\n- Minúsculas",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    break;
                }
            } while (true);

            // Atualizar senha
            credenciais.put(login, new String[] { novaSenha, credencialUsuario });
            FileManager.salvarCredenciaisNoCSV(credenciais);

            // Gerar ticket automaticamente
            geradorTicket.gerarTicketResetSenha(new String[] { login, novaSenha, credencialUsuario });

            statusLabel.setText("Senha resetada com sucesso para " + login + "!");
            statusLabel.setForeground(Color.GREEN);
        } catch (IOException ex) {
            statusLabel.setText("Erro ao resetar senha.");
            statusLabel.setForeground(Color.RED);
            ex.printStackTrace();
        }
    }
}