package sunmisc.mambo;

import sunmisc.mambo.complex.*;
import sunmisc.mambo.palette.CachedPalette;
import sunmisc.mambo.palette.ColorProperty;
import sunmisc.mambo.palette.Palette;
import sunmisc.mambo.palette.SimplePalette;

import java.awt.*;
import java.util.function.Supplier;

import static java.lang.Math.fma;

public final class MandelbrotMaker extends FrameMaker {

    private static final int MAX_ITERATIONS = 100;
    private static final Palette PALETTE =
            new CachedPalette(
                    new SimplePalette(
                            new ColorProperty(Math.toRadians(270), 4),
                            new ColorProperty(Math.toRadians(8), 4),
                            new ColorProperty(Math.toRadians(260), 4)
                    )
            );
    private final int hWidth, hHeight;
    private final Supplier<Number> scale;

    private final Point point;

    public MandelbrotMaker(int width, int height) {
        this(width, height, () -> 0.01);
    }

    public MandelbrotMaker(int width, int height, Point point) {
        this(width, height, () -> 0.01, point);
    }
    public MandelbrotMaker(int width, int height, Supplier<Number> scale) {
        this(width, height, scale, DEFAULT_POINT);
    }

    public MandelbrotMaker(int width, int height,
                           Supplier<Number> scale, Point point) {
        super(width, height);
        this.hWidth  = width >>> 1;
        this.hHeight = height >>> 1;
        this.scale = scale;
        this.point = point;
    }
    @Override
    public Color renderAt(int x, int y) {
        final double zoom = scale.get().doubleValue();

        int d1 = x - hWidth, d2 = y - hHeight;

        Complex start = new ComplexEnvelope(
                fma(zoom, d1, point.x().doubleValue()),
                fma(zoom, d2, point.y().doubleValue())
        );
        Complex curr = start;
        int itr = 0;
        for (; itr < MAX_ITERATIONS; ++itr) {
            if (new NormComplex(curr).intValue() >= 4)
                break;
            curr = new ComplexEnvelope(
                    new AddComplex(
                            new PowComplex(curr),
                            start
                    )
            );
        }
        return PALETTE.color(itr);
    }

    private static final int TARGET_INDEX_POINT = 1;

    private static final Point[] points = new Point[]{
            new PointA(-1.1900443, 0.3043895),
            new PointA(-0.10109636384562, 0.95628651080914),
            new PointA(-0.77568377, 0.13646737),
            new PointA(-0.7778078101931, 0.1316451080032),
            new PointA(-0.743643887037151, 0.13182590420533),
            new PointA(-1.2573680284665283, 0.3787308310286249),
            new PointA(-1.256640565451168862869, -0.382386428889165027247)
    };

    private static final Point DEFAULT_POINT = points[TARGET_INDEX_POINT];

    private record PointA(double x1, double y1) implements Point {
        @Override
        public Number x() {
            return x1;
        }

        @Override
        public Number y() {
            return y1;
        }
    }

}