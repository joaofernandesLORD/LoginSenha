import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GerarTicket {
    private static final String PASTA_LOG = "log";
    private static final String PREFIXO_ARQUIVO = "LogSistema_";
    private static final String SUFIXO_ARQUIVO = "_Administrativo.txt";
    private static final String SEPARADOR = "----------------------------------------\n";
    private static final SimpleDateFormat FORMATO_DATA_HORA = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Gera um ticket para cadastro de novo usuário
     * 
     * @param usuario Array com informações do usuário [login, senha, Credencial]
     */
    public void gerarTicketCadastroUsuario(String[] usuario) {
        String conteudo = "[NOVO USUÁRIO CADASTRADO]\n" +
                "Login: " + usuario[0] + "\n" +
                "Senha:" + usuario[1] + "\n" +
                "Credencial: " + usuario[2] + "\n" +
                "Data/Hora | " + FORMATO_DATA_HORA.format(new Date()) + "\n" +
                SEPARADOR;

        escreverNoLog(conteudo);
    }

    /**
     * Gera um ticket para remoção de usuário
     * 
     * @param login      Login do usuário removido
     * @param Credencial Credencial do usuário removido
     */
    public void gerarTicketRemocaoUsuario(String login, String Credencial) {
        String conteudo = "[USUÁRIO REMOVIDO]\n" +
                "Login: " + login + "\n" +
                "Credencial: " + Credencial + "\n" +
                "Data/Hora | " + FORMATO_DATA_HORA.format(new Date()) + "\n" +
                SEPARADOR;

        escreverNoLog(conteudo);
    }

    /**
     * Gera um ticket para modificação de login
     * 
     * @param usuario Array com informações do usuário [login, senha, Credencial]
     */
    public void gerarTicketModificacaoUsuario(String[] usuario) {
        String conteudo = "[LOGIN DE USUÁRIO MODIFICADO]\n" +
                "Novo Login: " + usuario[0] + "\n" +
                "Credencial: " + usuario[2] + "\n\n" +
                "Data/Hora | " + FORMATO_DATA_HORA.format(new Date()) + "\n" +
                SEPARADOR;

        escreverNoLog(conteudo);
    }

    /**
     * Gera um ticket para modificação de senha
     * 
     * @param usuario Array com informações do usuário [login, senha, Credencial]
     */
    public void gerarTicketModificacaoSenha(String[] usuario) {
        String conteudo = "[SENHA DE USUÁRIO MODIFICADA]\n" +
                "Login: " + usuario[0] + "\n" +
                "Nova senha: " + usuario[1] + "\n" +
                "Credencial: " + usuario[2] + "\n\n" +
                "Data/Hora | " + FORMATO_DATA_HORA.format(new Date()) + "\n" +
                SEPARADOR;

        escreverNoLog(conteudo);
    }

    /**
     * Gera um ticket para modificação de Credencial de usuário
     * 
     * @param usuario Array com informações do usuário [login, senha, Credencial]
     */
    public void gerarTicketModificacaoCredencial(String[] usuario) {
        String conteudo = "[Credencial DE USUÁRIO MODIFICADO]\n" +
                "Login: " + usuario[0] + "\n" +
                "Nova Credencial: " + usuario[2] + "\n\n" +
                "Data/Hor | " + FORMATO_DATA_HORA.format(new Date()) + "\n" +
                SEPARADOR;

        escreverNoLog(conteudo);
    }

    /**
     * Gera um ticket para reset de senha
     * 
     * @param usuario Array com informações do usuário [login, novaSenha,
     *                Credencial]
     */
    public void gerarTicketResetSenha(String[] usuario) {
        String conteudo = "[SENHA RESETADA]\n" +
                "Login: " + usuario[0] + "\n" +
                "Credencial: " + usuario[2] + "\n" +
                "Data/Hora | " + FORMATO_DATA_HORA.format(new Date()) + "\n" +
                SEPARADOR;

        escreverNoLog(conteudo);
    }

    /**
     * Método privado para escrever no arquivo de log
     * 
     * @param conteudo Conteúdo a ser escrito no log
     */
    private void escreverNoLog(String conteudo) {
        try {
            criarPastaLogSeNaoExistir();

            String nomeArquivo = PREFIXO_ARQUIVO + new SimpleDateFormat("yyyyMMdd").format(new Date()) + SUFIXO_ARQUIVO;
            File arquivoLog = new File(PASTA_LOG, nomeArquivo);

            // Escreve no console para debug
            System.out.println("[SISTEMA] Registrando operação:\n" + conteudo);

            // Escreve no arquivo de log
            try (FileWriter writer = new FileWriter(arquivoLog, true)) {
                writer.write(conteudo);
            }
        } catch (IOException ex) {
            System.err.println("[ERRO] Falha ao escrever no log: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Cria a pasta de logs se não existir
     */
    private void criarPastaLogSeNaoExistir() {
        File pastaLog = new File(PASTA_LOG);
        if (!pastaLog.exists()) {
            if (pastaLog.mkdir()) {
                System.out.println("[SISTEMA] Pasta de logs criada: " + pastaLog.getAbsolutePath());
            } else {
                System.err.println("[ERRO] Falha ao criar pasta de logs");
            }
        }
    }

    /**
     * Método para limpar logs antigos (opcional)
     * 
     * @param diasRetencao Número de dias para manter os logs
     */
    public void limparLogsAntigos(int diasRetencao) {
        // Implementação opcional para limpeza de logs antigos
        // Pode ser chamado periodicamente para manter a pasta de logs organizada
    }
}