import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    private static final String FILE_PATH = "credenciais.csv";
    private static final String CSV_HEADER = "login,senha,nivel";

    public static Map<String, String[]> readCredentialsFromCSV(String filePath) throws IOException {
        Map<String, String[]> credenciais = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String login = parts[0].trim();
                    String senha = parts[1].trim();
                    String nivel = parts[2].trim();

                    credenciais.put(login, new String[] { senha, nivel });
                }
            }
        }

        return credenciais;
    }

    public static void cadastrarNovoUsuario(String login, String senha, String nivel) throws IOException {
        File file = new File(FILE_PATH);
        boolean fileExists = file.exists() && !file.isDirectory();

        try (FileWriter fw = new FileWriter(FILE_PATH, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {

            // Se o arquivo não existir ou estiver vazio, escreve o cabeçalho
            if (!fileExists || file.length() == 0) {
                out.println(CSV_HEADER);
            }

            out.println(login + "," + senha + "," + nivel);
        }
    }

    public static void salvarCredenciaisNoCSV(Map<String, String[]> credenciais) throws IOException {
        try (FileWriter fw = new FileWriter(FILE_PATH);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {

            out.println(CSV_HEADER);
            for (Map.Entry<String, String[]> entry : credenciais.entrySet()) {
                out.println(entry.getKey() + "," + entry.getValue()[0] + "," + entry.getValue()[1]);
            }
        }
    }
}