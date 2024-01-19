package sunmisc.mambo;

import sunmisc.mambo.complex.*;
import sunmisc.mambo.palette.CachedPalette;
import sunmisc.mambo.palette.ColorProperty;
import sunmisc.mambo.palette.Palette;
import sunmisc.mambo.palette.SimplePalette;

import java.util.function.DoubleSupplier;

import static java.lang.Math.fma;

public final class MandelbrotMaker extends FrameMaker {

    private static final int MAX_ITERATIONS = 400;
    private static final Palette PALETTE =
            new CachedPalette(
                    new SimplePalette(
                            new ColorProperty(Math.toRadians(30), 4),
                            new ColorProperty(Math.toRadians(60), 4),
                            new ColorProperty(Math.toRadians(360), 4)
                    )
            );
    private final int half_width, half_height;
    private final DoubleSupplier scale;

    public MandelbrotMaker(int width, int height) {
        this(width, height, () -> 0.01);
    }

    public MandelbrotMaker(int width, int height, DoubleSupplier scale) {
        super(width, height);
        this.half_width  =  width >>> 1;
        this.half_height = height >>> 1;
        this.scale = scale;
    }


    @Override
    public int renderAt(int x, int y) {
        final double zoom = scale.getAsDouble();

        double d1 = x - half_width, d2 = y - half_height;

        Complex start = new ComplexEnvelope(
                fma(zoom, d1, point.x()),
                fma(zoom, d2, point.y())
        );
        Complex curr = start;
        int itr = 0;
        for (; itr < MAX_ITERATIONS; ++itr) {
            if (new NormComplex(curr).floatValue() >= 4F)
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

    private static final int TARGET_INDEX_POINT = 1;

    private static final Point[] points = new Point[]{
            new Point(-1.1900443, 0.3043895),
            new Point(-0.10109636384562, 0.95628651080914),
            new Point(-0.77568377, 0.13646737),
            new Point(-0.7778078101931, 0.1316451080032),
            new Point(-0.743643887037151, 0.13182590420533),
            new Point(-1.2573680284665283, 0.3787308310286249),
            new Point(-1.256640565451168862869, -0.382386428889165027247)
    };

    private static final Point point = points[TARGET_INDEX_POINT];

    private record Point(double x, double y) { }

}