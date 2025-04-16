import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GerarTicket {
    private static final String PASTA_LOG = "log";
    private static final String PREFIXO_ARQUIVO = "LogSistema_";
    private static final String SUFIXO_ARQUIVO = "_Administrativo.txt";
    private static final String SEPARADOR = "----------------------------------------\n";
    private static final SimpleDateFormat FORMATO_DATA_HORA = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public void gerarTicketCadastroUsuario(String[] usuario) {
        String conteudo = "[NOVO USUÁRIO CADASTRADO]\n" +
                "Login: " + usuario[0] + "\n" +
                "Senha:" + usuario[1] + "\n" +
                "Credencial: " + usuario[2] + "\n" +
                "Data/Hora | " + FORMATO_DATA_HORA.format(new Date()) + "\n" +
                SEPARADOR;
        escreverNoLog(conteudo);
    }

    public void gerarTicketRemocaoUsuario(String login, String Credencial) {
        String conteudo = "[USUÁRIO REMOVIDO]\n" +
                "Login: " + login + "\n" +
                "Credencial: " + Credencial + "\n" +
                "Data/Hora | " + FORMATO_DATA_HORA.format(new Date()) + "\n" +
                SEPARADOR;
        escreverNoLog(conteudo);
    }

    public void gerarTicketModificacaoUsuario(String[] usuario) {
        String conteudo = "[LOGIN DE USUÁRIO MODIFICADO]\n" +
                "Novo Login: " + usuario[0] + "\n" +
                "Credencial: " + usuario[2] + "\n\n" +
                "Data/Hora | " + FORMATO_DATA_HORA.format(new Date()) + "\n" +
                SEPARADOR;
        escreverNoLog(conteudo);
    }

    public void gerarTicketModificacaoSenha(String[] usuario) {
        String conteudo = "[SENHA DE USUÁRIO MODIFICADA]\n" +
                "Login: " + usuario[0] + "\n" +
                "Nova senha: " + usuario[1] + "\n" +
                "Credencial: " + usuario[2] + "\n\n" +
                "Data/Hora | " + FORMATO_DATA_HORA.format(new Date()) + "\n" +
                SEPARADOR;
        escreverNoLog(conteudo);
    }

    public void gerarTicketModificacaoCredencial(String[] usuario) {
        String conteudo = "[Credencial DE USUÁRIO MODIFICADO]\n" +
                "Login: " + usuario[0] + "\n" +
                "Nova Credencial: " + usuario[2] + "\n\n" +
                "Data/Hor | " + FORMATO_DATA_HORA.format(new Date()) + "\n" +
                SEPARADOR;

        escreverNoLog(conteudo);
    }

    public void gerarTicketResetSenha(String[] usuario) {
        String conteudo = "[SENHA RESETADA]\n" +
                "Login: " + usuario[0] + "\n" +
                "Credencial: " + usuario[2] + "\n" +
                "Data/Hora | " + FORMATO_DATA_HORA.format(new Date()) + "\n" +
                SEPARADOR;
        escreverNoLog(conteudo);
    }

    private void escreverNoLog(String conteudo) {
        try {
            criarPastaLogSeNaoExistir();

            String nomeArquivo = PREFIXO_ARQUIVO + new SimpleDateFormat("yyyyMMdd").format(new Date()) + SUFIXO_ARQUIVO;
            File arquivoLog = new File(PASTA_LOG, nomeArquivo);
            System.out.println("[SISTEMA] Registrando operação:\n" + conteudo);
            try (FileWriter writer = new FileWriter(arquivoLog, true)) {
                writer.write(conteudo);
            }
        } catch (IOException ex) {
            System.err.println("[ERRO] Falha ao escrever no log: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

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

    public void limparLogsAntigos(int diasRetencao) {
    }
}