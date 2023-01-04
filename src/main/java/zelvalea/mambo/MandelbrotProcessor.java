package zelvalea.mambo;

public final class MandelbrotProcessor extends FrameMaker {

    private static final int MAX_ITERATIONS = 600;
    private static final float CENTER_X = -0.77568377F;
    private static final float CENTER_Y  = 0.13646737F;

    public MandelbrotProcessor(int width, int height) {
        super(width, height);
    }

    @Override
    public void chunkRender(int x_from, int x_to,
                            int y_from, int y_to,
                            int[] data) {
        for (int x = x_from; x < x_to; x++) {
            for (int y = y_from; y < y_to; y++) {

                float xH = size >> 1;
                float yH = size >> 1;

                float cX = (y - xH) * scale + CENTER_X;
                float cY = (x - yH) * scale + CENTER_Y;

                float x2 = 0, y2 = 0;
                float w = 0;

                int i = 0;

                while (x2 + y2 <= 4 && i < MAX_ITERATIONS) {
                    float x1 = x2 - y2 + cX;
                    float y1 = w - x2 - y2 + cY;

                    x2 = x1 * x1;
                    y2 = y1 * y1;

                    w = (x1 + y1) * (x1 + y1);

                    i++;
                }

                data[size * x + y] = (i << 21) + (i << 10) + i*4;
            }
        }
    }
}