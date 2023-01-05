package zelvalea.mambo;

import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;

public abstract class FrameMaker {

    static final int MIN_PARTITION = 1 << 12;

    static final int NCPU = ForkJoinPool.getCommonPoolParallelism();

    protected final int width, height;



    public FrameMaker(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void renderAt(int index, int[] data);


    public void render(int[] data) {

        int n = data.length;
        int threshold = Math.max(n / (NCPU << 1), Math.min(n, MIN_PARTITION));

        new BulkTask(null, this,
                data, threshold,
                0, n
        ).invoke();
    }

    static final class BulkTask extends CountedCompleter<Void> {

        final int[] data;

        final FrameMaker maker;
        final int threshold;

        final int hi, lo;

        public BulkTask(BulkTask parent,
                        FrameMaker maker,
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

            if (size < threshold) {
                for (int i = lo; i < hi; ++i) {
                    maker.renderAt(i, data);
                }
            } else {
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
    }

}