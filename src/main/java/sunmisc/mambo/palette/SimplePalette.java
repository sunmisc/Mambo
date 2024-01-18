package sunmisc.mambo.palette;

import java.awt.*;

public class SimplePalette implements Palette {
    private final ColorProperty red, green, blue;

    public SimplePalette(ColorProperty red,
                         ColorProperty green,
                         ColorProperty blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public Color color(int i) {
        return new Color(
                red.applyAsInt(i),
                green.applyAsInt(i),
                blue.applyAsInt(i)
        );
    }
}
