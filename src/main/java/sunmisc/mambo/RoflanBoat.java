package sunmisc.mambo;

import sunmisc.mambo.palette.CachedPalette;
import sunmisc.mambo.palette.ColorProperty;
import sunmisc.mambo.palette.Palette;
import sunmisc.mambo.palette.SimplePalette;

import java.util.Map;

import static java.lang.Math.*;
import static java.util.Map.entry;

public class RoflanBoat extends FrameMaker {
    private static final int MAX_ITERATIONS = 500;
    private static final Palette PALETTE = new CachedPalette(
            new SimplePalette(
                    new ColorProperty(Math.toRadians(270), 4),
                    new ColorProperty(Math.toRadians(30), 4),
                    new ColorProperty(Math.toRadians(60), 4)
            )
    );
    private static final int TARGET_INDEX_PONT = 0;
    @SuppressWarnings("unchecked")
    private static final Map.Entry<Double, Double>[] points = new Map.Entry[]{
            entry(-1.762, -0.028),
            entry(-1.750, -0.0001)

    };
    private static final double CENTER_X, CENTER_Y;
    static {
        Map.Entry<Double,Double> entry = points[TARGET_INDEX_PONT];

        CENTER_X = entry.getKey();
        CENTER_Y = entry.getValue();
    }


    private final int half_width, half_height;

    private double scale = 1.75;

    public RoflanBoat(int width, int height) {
        super(width, height);
        this.half_width  =  width >>> 1;
        this.half_height = height >>> 1;
    }

    @Override
    public int[] render() {
        scale *= 0.97;
        return super.render();

    }
    @Override
    public int renderAt(int x, int y) {

        double zoom = scale;

        double d1 = y - half_width, d2 = x - half_height;

        // a * b + c
        double real = fma(zoom, d1, CENTER_X);
        double imag = fma(zoom, d2, CENTER_Y);

        double zx = real, zy = imag;


        double x_pow = 0, y_pow = 0;

        int itr;

        for (itr = 0; x_pow + y_pow <= 4 && itr < MAX_ITERATIONS; ++itr) {
            x_pow = pow(zx, 2);
            y_pow = pow(zy, 2);

            zy = abs(2 * zx * zy) + imag;
            zx = x_pow - y_pow + real;
        }

        return PALETTE.color(itr).getRGB();
    }
}
