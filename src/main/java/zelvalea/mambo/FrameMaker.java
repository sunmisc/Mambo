package zelvalea.mambo;


import java.util.concurrent.*;

public abstract class FrameMaker {

    static final int NCPU = ForkJoinPool.getCommonPoolParallelism();

    protected final int size;
    protected final int threshold;
    protected float scale = 2F;


    public FrameMaker(int width, int height) {
        final int sz = (width + height) >> 1;
        this.size = sz;
        this.threshold = sz / NCPU;
    }

    public abstract void chunkRender(int x_from, int x_to,
                                     int y_from, int y_yo,
                                     int[] data);


    public void render(int[] data) {

        new BulkTask( data, 0, 0, size, size).invoke();

        scale *= 0.95F;
    }

    private final class BulkTask extends RecursiveAction {
        private final int[] data;
        private final int x1, y1;
        private final int x2, y2;

        public BulkTask(int[] data, int x1, int y1, int x2, int y2) {
            this.data = data;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        @Override
        public void compute() {
            if ((x2 - x1) * (y2 - y1) <= threshold) {
                chunkRender(
                        x1, Math.min(x2, data.length),
                        y1, Math.min(y2, data.length),
                        data
                );
            } else {
                final int xMid = (x1 + x2) >>> 1;
                final int yMid = (y1 + y2) >>> 1;
                invokeAll(
                        new BulkTask(data, x1, y1, xMid, yMid),
                        new BulkTask(data, x1, yMid, xMid, y2),
                        new BulkTask(data, xMid, y1, x2, yMid),
                        new BulkTask(data, xMid, yMid, x2, y2)
                );
            }
        }
    }
}