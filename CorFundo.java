import java.awt.Color;

public class CorFundo {

    public static Color getCorFundo(String nomeCor) {
        switch (nomeCor.toLowerCase()) {
            case "Fundo Principal":
                return new Color(72, 58, 139);
            case "Fundo Segundario":
                return new Color(7, 33, 74);
            case "Fundo Terceiro":
                return new Color(25, 61, 117);
            default:
                return new Color(7, 20, 41);
        }
    }

    public static Color getCorPersonalizada(int r, int g, int b) {
        return new Color(r, g, b);
    }
}