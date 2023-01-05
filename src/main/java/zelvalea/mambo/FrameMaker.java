package zelvalea.mambo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public abstract class FrameMaker {

    static final int MIN_PARTITION = 32;

    static final int NCPU = ForkJoinPool.getCommonPoolParallelism();

    protected final int width, height;
    protected double scale = 2;


    public FrameMaker(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void chunkRender(int x_from, int x_to,
                                     int y_from, int y_to,
                                     int[] data);


    public void render(int[] data) {

        int threshold = data.length;

        threshold /= (NCPU << 1);
        threshold = Math.max(threshold, MIN_PARTITION);

        new BulkTask(data, threshold, 0, 0, width, height).invoke();

        scale *= 0.965D;
    }

    private final class BulkTask extends RecursiveAction {
        private final int[] data;
        private final int threshold;
        private final int xlo, ylo;
        private final int xhi, yhi;

        BulkTask(int[] data, int threshold,
                 int xlo, int ylo,
                 int xhi, int yhi) {
            this.data = data;
            this.threshold = threshold;
            this.xlo = xlo;
            this.ylo = ylo;
            this.xhi = xhi;
            this.yhi = yhi;
        }

        @Override
        public void compute() {
            final int t = threshold;
            if ((xhi - xlo) * (yhi - ylo) < t) {
                chunkRender(
                        xlo, Math.min(xhi, width),
                        ylo, Math.min(yhi, height),
                        data
                );
            } else {
                final int xMid = (xlo + xhi) >>> 1;
                final int yMid = (ylo + yhi) >>> 1;
                invokeAll(
                        new BulkTask(data, t, xlo, ylo, xMid, yMid),
                        new BulkTask(data, t, xlo, yMid, xMid, yhi),
                        new BulkTask(data, t, xMid, ylo, xhi, yMid),
                        new BulkTask(data, t, xMid, yMid, xhi, yhi)
                );
            }
        }
    }
}