package sunmisc.mambo.palette;

import java.awt.*;

public class CachedPalette implements Palette {

    private final Color[] colors = new Color[255];

    private final Palette origin;


    public CachedPalette(Palette origin) {
        this.origin = origin;
    }

    @Override
    public Color color(int index) {
        index %= 255;
        Color color = colors[index];
        return color == null
                ? (colors[index] = origin.color(index))
                : color;
    }
}
