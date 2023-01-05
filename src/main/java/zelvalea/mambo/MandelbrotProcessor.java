package zelvalea.mambo;

public final class MandelbrotProcessor extends FrameMaker {
    private static final double MANDELBROT_FRACTAL_LENGTH = 2.25;
    private static final int MAX_ITERATIONS = 500;
    private static final double CENTER_X = -0.10109636384562;
    private static final double CENTER_Y  = 0.95628651080914;


    /*private static final double CENTER_X = -0.77568377;
    private static final double CENTER_Y  = 0.13646737;*/

    private final int half_width, half_height;

    private double scale = 2;

    public MandelbrotProcessor(int width, int height) {
        super(width, height);
        this.half_width  =  width >>> 1;
        this.half_height = height >>> 1;
    }

    @Override
    public void render(int[] data) {

        super.render(data);

        this.scale *= 0.96;
    }

    @Override
    public void renderAt(int i, int[] data) {

        final double zoom = scale;

        final int x = i / height, y = i % width;

        double d1 = y - half_width, d2 = x - half_height;

        // a * b + c
        double real = Math.fma(zoom, d1, CENTER_X);
        double imag = Math.fma(zoom, d2, CENTER_Y);

        double x1 = real, y1 = imag;


        double x_pow = 0, y_pow = 0;

        int itr;

        for (itr = MAX_ITERATIONS; x_pow + y_pow <= 4 && itr >= 0; --itr) {
            x_pow = x1 * x1;
            y_pow = y1 * y1;

            y1 = 2 * x1 * y1 + imag;
            x1 = x_pow - y_pow + real;
        }

        data[i] = (itr << 21) | (itr << 10) | (itr << 2);
    }
}