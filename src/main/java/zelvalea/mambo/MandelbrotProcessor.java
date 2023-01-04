package zelvalea.mambo;

public final class MandelbrotProcessor extends FrameMaker {

    private static final int MAX_ITERATIONS = 500;
    private static final double CENTER_X = -0.77568377;
    private static final double CENTER_Y  = 0.13646737;

    private final int half;

    public MandelbrotProcessor(int width, int height) {
        super(width, height);
        this.half = size >>> 1;
    }

    @Override
    public void chunkRender(int x_from, int x_to,
                            int y_from, int y_to,
                            int[] data) {

        for (int x = x_from; x < x_to; x++) {
            double imag = (x - half) * scale + CENTER_Y;
            for (int y = y_from; y < y_to; y++) {
                double real = (y - half) * scale + CENTER_X;

                double x1 = real, y1 = imag;


                double x_pow = 0, y_pow = 0;

                int i = 0;

                while (x_pow + y_pow < 4 && i < MAX_ITERATIONS) {
                    if (x_pow + y_pow >= 4) break;

                    x_pow = x1 * x1; y_pow = y1 * y1;

                    double tmp = x_pow - y_pow + real;
                    y1 = 2 * x1 * y1 + imag;
                    x1 = tmp;
                    i++;
                }

                data[size * x + y] = (i << 21) + (i << 10) + i*4;
            }
        }
    }
}