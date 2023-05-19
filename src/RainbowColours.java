import java.awt.Color;

public class RainbowColours {
    static Color red = new Color(255, 0, 0);
    static Color orange = new Color(255, 153, 0);
    static Color yellow = new Color(255, 255, 0);
    static Color green = new Color(0, 255, 0);
    static Color blue = new Color(0, 0, 255);
    static Color indigo = new Color(75, 0, 130);
    static Color violet = new Color(255, 0, 255);
    static Color darkred = new Color(139, 0, 0);

    public static Color[] getColours(){
        Color[] colours = { red, orange, yellow, green, blue, indigo, violet, darkred };
        return colours;
    }

    public static Color mixture(Color c1, Color c2, double p) {
        int red = (int) (c1.getRed() * p + c2.getRed() * (1 - p));
        int green = (int) (c1.getGreen() * p + c2.getGreen() * (1 - p));
        int blue = (int) (c1.getBlue() * p + c2.getBlue() * (1 - p));
        return new Color(red, green, blue);
    }
}
