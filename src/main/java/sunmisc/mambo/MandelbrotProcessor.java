package sunmisc.mambo;

import sunmisc.mambo.complex.*;
import sunmisc.mambo.palette.CachedPalette;
import sunmisc.mambo.palette.ColorProperty;
import sunmisc.mambo.palette.Palette;
import sunmisc.mambo.palette.SimplePalette;

import java.util.Map;

import static java.lang.Math.fma;
import static java.util.Map.entry;

public final class MandelbrotProcessor extends FrameMaker {
    private static final int TARGET_INDEX_PONT = 5;
    @SuppressWarnings("unchecked")
    private static final Map.Entry<Double, Double>[] points = new Map.Entry[]{
            entry(-1.1900443, 0.3043895),
            entry(-0.10109636384562, 0.95628651080914),
            entry(-0.77568377, 0.13646737),
            entry(-0.7778078101931, 0.1316451080032),
            entry(-0.743643887037151, 0.13182590420533),
            entry(-1.2573680284665283, 0.3787308310286249),
            entry(-1.256640565451168862869, -0.382386428889165027247)

    };
    private static final double CENTER_X, CENTER_Y;
    static {
        Map.Entry<Double,Double> entry = points[TARGET_INDEX_PONT];

        CENTER_X = entry.getKey();
        CENTER_Y = entry.getValue();
    }


    private static final int MAX_ITERATIONS = 100;
    private static final Palette PALETTE =
            new CachedPalette(
                    new SimplePalette(
                            new ColorProperty(Math.toRadians(30), 4),
                            new ColorProperty(Math.toRadians(60), 4),
                            new ColorProperty(Math.toRadians(360), 4)
                    )
            );
    private final int half_width, half_height;

    private double scale = 1.75;


    public MandelbrotProcessor(int width, int height) {
        super(width, height);
        this.half_width  =  width >>> 1;
        this.half_height = height >>> 1;
    }

    @Override
    public int[] render() {
        try {
            return super.render();
        } finally {
            scale *= 0.97;
        }
    }

    @Override
    public int renderAt(int x, int y) {
        final double zoom = scale;

        double d1 = y - half_width, d2 = x - half_height;

        Complex start = new ComplexEnvelope(
                fma(zoom, d1, CENTER_X),
                fma(zoom, d2, CENTER_Y)
        );
        Complex curr = start;
        int itr = 0;
        for (; itr < MAX_ITERATIONS; ++itr) {
            if (new ModuleSqrComplex(curr).intValue() >= 4)
                break;
            curr = new ComplexEnvelope(
                    new AddComplex(
                            new MultipleComplex(curr, curr),
                            start
                    )
            );
        }
        return PALETTE.color(itr).getRGB();
    }
}