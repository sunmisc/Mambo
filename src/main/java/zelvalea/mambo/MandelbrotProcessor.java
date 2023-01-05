package zelvalea.mambo;

public final class MandelbrotProcessor extends FrameMaker {

    private static final int MAX_ITERATIONS = 500;
    private static final double CENTER_X = -0.77568377;
    private static final double CENTER_Y  = 0.13646737;

    private final int half_width, half_height;

    public MandelbrotProcessor(int width, int height) {
        super(width, height);
        this.half_width  =  width >>> 1;
        this.half_height = height >>> 1;
    }


    @Override
    public void chunkRender(int x_from, int x_to,
                            int y_from, int y_to,
                            int[] data) {
        for (int x = x_from; x < x_to; x++) {
            double imag = (x - half_width) * scale + CENTER_Y;
            for (int y = y_from; y < y_to; y++) {
                double real = (y - half_height) * scale + CENTER_X;

                double x1 = real, y1 = imag;


                double x_pow = 0, y_pow = 0;

                int i = MAX_ITERATIONS;

                while (x_pow + y_pow < 4 && i >= 0) {

                    x_pow = x1 * x1; y_pow = y1 * y1;

                    double tmp = x_pow - y_pow + real;
                    y1 = 2 * x1 * y1 + imag;
                    x1 = tmp;
                    i--;
                }

                data[width * y + x] = (i << 21) | (i << 10) | (i << 2);
            }
        }
    }
}