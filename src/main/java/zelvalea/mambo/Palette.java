package zelvalea.mambo;

import java.awt.*;

public class Palette {
    private final Color[] colors;

    public Palette(int minColor, int maxColor,
                   double rStart, double gStart, double bStart,
                   int rSteps, int gSteps, int bSteps) {
        int totalRange = maxColor - minColor;

        colors = new Color[totalRange];

        int rRange = totalRange / rSteps;
        int gRange = totalRange / gSteps;
        int bRange = totalRange / bSteps;

        for (int i = 0; i < totalRange; i++) {
            double cosR = Math.cos(i * Math.TAU / rRange + rStart);
            double cosG = Math.cos(i * Math.TAU / gRange + gStart);
            double cosB = Math.cos(i * Math.TAU / bRange + bStart);
            Color color = new Color(
                    (int) ((cosR * totalRange) + totalRange) / 2 + minColor,
                    (int) ((cosG * totalRange) + totalRange) / 2 + minColor,
                    (int) ((cosB * totalRange) + totalRange) / 2 + minColor);
            colors[i] = color;
        }
    }

    public Color getColor(int index) {
        int n = colors.length;
        return colors[index % n];
    }

    public int getSize() {
        return colors.length;
    }
}
