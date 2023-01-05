package zelvalea.mambo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public abstract class FrameMaker {

    static final int MIN_PARTITION = 32;

    static final int NCPU = ForkJoinPool.getCommonPoolParallelism();

    protected final int width, height;
    protected double scale = 2;

    private final int threshold;


    public FrameMaker(int width, int height) {
        this.width = width;
        this.height = height;

        int threshold = width * height;

        threshold /= (NCPU << 1);
        threshold = Math.max(threshold, MIN_PARTITION);

        this.threshold = threshold;

    }

    public abstract void chunkRender(int x_from, int x_to,
                                     int y_from, int y_to,
                                     int[] data);


    public void render(int[] data) {

        new BulkTask(data, 0, 0, width, height).invoke();

        scale *= 0.965D;
    }

    private final class BulkTask extends RecursiveAction {
        private final int[] data;
        private final int xlo, ylo;
        private final int xhi, yhi;

        BulkTask(int[] data,
                 int xlo, int ylo,
                 int xhi, int yhi) {
            this.data = data;
            this.xlo = xlo;
            this.ylo = ylo;
            this.xhi = xhi;
            this.yhi = yhi;
        }

        @Override
        public void compute() {
            if ((xhi - xlo) * (yhi - ylo) < threshold) {
                chunkRender(
                        xlo, xhi,
                        ylo, yhi,
                        data
                );
            } else {
                final int xMid = (xlo + xhi) >>> 1;
                final int yMid = (ylo + yhi) >>> 1;
                invokeAll(
                        new BulkTask(data, xlo, ylo, xMid, yMid),
                        new BulkTask(data, xlo, yMid, xMid, yhi),
                        new BulkTask(data, xMid, ylo, xhi, yMid),
                        new BulkTask(data, xMid, yMid, xhi, yhi)
                );
            }
        }
    }
}