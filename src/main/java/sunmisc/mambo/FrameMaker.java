package sunmisc.mambo;

import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;

public abstract class FrameMaker {

    static final int MIN_PARTITION = 1 << 12;

    static final int NCPU = ForkJoinPool.getCommonPoolParallelism();

    private final int width, height;
    private final int[] data;

    public FrameMaker(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new int[width * height];
    }

    public abstract int renderAt(int x, int y);


    public int[] render() {
        int n = data.length;
        int threshold = Math.max(n / (NCPU << 3), Math.min(n, MIN_PARTITION));
        return new BulkTask(null, this,
                data, threshold,
                0, n
        ).invoke();
    }

    private void renderChunk(int lo, int hi, int[] data) {

        for (int i = lo; i < hi; ++i) {
            final int x = i / height, y = i % width;

            data[i] = renderAt(x, y);
        }

    }

    private static final class BulkTask extends CountedCompleter<int[]> {

        private final int[] data;
        private final FrameMaker maker;
        private final int threshold;

        private final int hi, lo;

        BulkTask(BulkTask parent, FrameMaker maker,
                 int[] data, int threshold,
                 int lo, int hi) {
            super(parent);
            this.maker = maker;
            this.data = data;
            this.threshold = threshold;
            this.lo = lo; this.hi = hi;
        }


        @Override
        public void compute() {

            final int size = hi - lo;

            if (size < threshold)
                maker.renderChunk(lo, hi, data);
            else {
                setPendingCount(2);
                int mid = (lo + hi) >>> 1;
                new BulkTask(this, maker,
                        data, threshold,
                        mid, hi
                ).fork();
                new BulkTask(this, maker,
                        data, threshold,
                        lo, mid
                ).fork();
            }
            propagateCompletion();
        }


        @Override
        public int[] getRawResult() {
            return data;
        }
    }

}